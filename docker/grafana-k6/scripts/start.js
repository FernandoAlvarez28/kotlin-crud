import http from 'k6/http';
import { check, group } from 'k6';
import * as utils from './utils.js';

export const options = {
	scenarios: {
		apiProductsGet: {
			executor: 'ramping-vus',
			startTime: '0s',
			stages: [
				{ target: 40, duration: '15s' }, //the firsts users starts to see the products
				{ target: 400, duration: '2m' }, //the "peak demand"
				{ target: 40, duration: '15s' },
			],
			tags: {
				name: 'products_listing'
			},
			exec: 'apiProductsGet', //the function this scenario will execute
		},
		apiPurchasePost: {
			executor: 'ramping-vus',
			startTime: '15s', //they start to buy after some time
			stages: [
				{ target: 40, duration: '15s' }, //the firsts users starts to buy
				{ target: 260, duration: '1m45s' },
				{ target: 30, duration: '15s' },
			],
			tags: {
				name: 'purchase_flow'
			},
			exec: 'apiPurchasePost', //the function this scenario will execute
		}
	},
	thresholds: {
		//http_req_failed and checks metrics for each scenario are programmatically added by addThresholdForAllScenarios()
		'http_req_duration{scenario:apiProductsGet}': ['p(90)<225', 'p(95)<250'],
		'http_req_duration{scenario:apiPurchasePost}': ['p(90)<1150', 'p(95)<1250'],
	},
};

utils.addThresholdForAllScenarios(options, 'http_req_failed', 'rate<0.01');
utils.addThresholdForAllScenarios(options, 'checks', 'rate>99');

export function setup() {
	const setupData = {};

	group('Authenticate', function() {
		const loginBody = {
			"username": "temp orary",
			"password": "withoutPassword"
		};

		const loginResponse = http.post(
			'http://host.docker.internal:8080/api/v1/login',
			JSON.stringify(loginBody),
			{
				headers: {'Content-Type': 'application/json'},
				tags: {name: "POST /api/v1/login"}
			}
		);

		setupData.authorizationHeaderValue = `Bearer ${loginResponse.headers['Authorization']}`
	});

	group('Select the most available product to test', function() {
		//Considering there is a test product with enough available quantity to use in load tests
		const productsResponse = fetchProducts(setupData.authorizationHeaderValue);
		if (productsResponse.status !== 200) {
			return;
		}

		setupData.mostAvailableProduct = utils.getMostAvailableProduct(productsResponse.json());
	});

	return setupData;
}

export function apiProductsGet(setupData) {
	group('List products', function() {
		const response = fetchProducts(setupData.authorizationHeaderValue);
		check(response, { "status is 200": (r) => r.status === 200 });
	});
}

export function apiPurchasePost(setupData) {
	var selectedProduct = setupData.mostAvailableProduct;

	group('Purchase product', function() {
		const postData = {
			"selectedProducts": [
				{
					"id": selectedProduct.id,
					"quantity": 1
				}
			]
		};

		const postResponse = http.post(
			'http://host.docker.internal:8080/api/v1/purchases',
			JSON.stringify(postData),
			{
				headers: {
					'Content-Type': 'application/json',
					'Authorization': setupData.authorizationHeaderValue
				},
				tags: {name: "POST /api/v1/purchases"}
			}
		);
		check(postResponse, { "status is 201": (r) => r.status === 201 });



		const getResponse = http.get(
			'http://host.docker.internal:8080/api/v1/purchases/' + postResponse.json().id,
			{
				headers: {
					'Authorization': setupData.authorizationHeaderValue
				},
				tags: {name: "GET /api/v1/purchases/{id}"}
			}
		);
		check(getResponse, { "status is 200": (r) => r.status === 200 });
	});
}

function fetchProducts(authorizationHeaderValue) {
	return http.get('http://host.docker.internal:8080/api/v1/products', {
		headers: {'Authorization': authorizationHeaderValue},
		tags: {name: "GET /api/v1/products"}
	});
}
