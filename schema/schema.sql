
DROP TABLE IF EXISTS purchase_orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS suppliers;




CREATE TABLE suppliers(
    supplier_id SERIAL PRIMARY KEY,
    supplier_name VARCHAR(100) UNIQUE NOT NULL,
    contact_person TEXT,
    phone_number VARCHAR(20),
    email_address VARCHAR(100) 
    );


CREATE TABLE products(
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(150) UNIQUE NOT NULL,
    description TEXT,
    unit_price NUMERIC(10, 2) NOT NULL CHECK (unit_price > 0),
    stock_level INT NOT NULL DEFAULT 0 CHECK (stock_level >= 0)
    );

CREATE TABLE purchase_orders(
    order_id SERIAL PRIMARY KEY,
    order_quantity INT NOT NULL CHECK (order_quantity > 0),
    order_date DATE NOT NULL DEFAULT CURRENT_DATE,
    supplier_id INT REFERENCES suppliers(supplier_id) NOT NULL,
    product_id INT REFERENCES products(product_id) NOT NULL
    );

