export function addThresholdForAllScenarios(options, thresholdName, thresholdValue) {
	for (let key in options.scenarios) {
		// Generate a name for each scenario
		let thresholdFullName = `${thresholdName}{scenario:${key}}`;

		// Check to prevent overwriting a threshold that already exists with an empty array
		if (!options.thresholds[thresholdFullName]) {
			options.thresholds[thresholdFullName] = [];
		}

		options.thresholds[thresholdFullName].push(thresholdValue);
	}
}

export function getMostAvailableProduct(products) {
	let mostAvailableProduct = null;

	products.forEach(product => {
		if (mostAvailableProduct == null || product.availableQuantity > mostAvailableProduct.availableQuantity) {
			mostAvailableProduct = product;
		}
	});

	return mostAvailableProduct;
}
