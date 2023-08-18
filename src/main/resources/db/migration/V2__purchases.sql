CREATE TABLE kotlin_crud.PURCHASE (
	  id UUID DEFAULT UUID_IN(MD5(RANDOM()::TEXT||NOW()::TEXT)::CSTRING) PRIMARY KEY
	, total_value NUMERIC NOT NULL
	, total_products INT NOT NULL
	, purchased_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);