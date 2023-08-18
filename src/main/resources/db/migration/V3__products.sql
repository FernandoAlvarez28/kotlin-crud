CREATE TABLE kotlin_crud.PRODUCT (
	  id UUID DEFAULT UUID_IN(MD5(RANDOM()::TEXT||NOW()::TEXT)::CSTRING) PRIMARY KEY
	, name VARCHAR NOT NULL
	, unit_price NUMERIC NOT NULL
	, available_quantity INT NOT NULL
	, added_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE kotlin_crud.PURCHASED_PRODUCT (
	  purchase_id UUID NOT NULL
	, product_id UUID NOT NULL
	, product_name VARCHAR NOT NULL
	, unit_price NUMERIC NOT NULL
	, quantity INT NOT NULL
	, paid_price NUMERIC NOT NULL
	, CONSTRAINT purchased_product_purchase_id_fk FOREIGN KEY (purchase_id) REFERENCES kotlin_crud.PURCHASE (id)
	, CONSTRAINT purchased_product_product_id_fk FOREIGN KEY (product_id) REFERENCES kotlin_crud.PRODUCT (id)
);

INSERT INTO kotlin_crud.PRODUCT (name, unit_price, available_quantity) VALUES ('Wonder Woman''s invisible plane', 6816773.7, 1);
INSERT INTO kotlin_crud.PRODUCT (name, unit_price, available_quantity) VALUES ('Sonic Stopstation 5', 539.99, 200);
INSERT INTO kotlin_crud.PRODUCT (name, unit_price, available_quantity) VALUES ('Macrosoft Xcrate One', 249.99, 360);
INSERT INTO kotlin_crud.PRODUCT (name, unit_price, available_quantity) VALUES ('Nashi Pear YouPhone', 899.00, 15); --Nashi pear is a fruit visually similar to an apple
INSERT INTO kotlin_crud.PRODUCT (name, unit_price, available_quantity) VALUES ('Air', 0.01, 2147483647); --To test with K6
