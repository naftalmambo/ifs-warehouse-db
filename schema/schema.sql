

CREATE TABLE suppliers(
    supplier_name SERIAL PRIMARY KEY,
    supplier_name VARCHAR(100) UNIQUE NOT NULL,
    contact_person TEXT,
    phone_number INT,
    email_address VARCHAR(100) 




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