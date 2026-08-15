

CREATE TABLE suppliers(


);

CREATE TABLE products(
    Product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(150) UNIQUE NOT NULL,
    description TEXT,
    unit_price NUMERIC(10, 2) NOT NULL CHECK (unit_price > 0),
    stock_level INT NOT NULL DEFAULT 0 CHECK (stock_level >= 0)
);

CREATE TABLE purchase_orders(



);

CREATE TABLE stock_level(


);