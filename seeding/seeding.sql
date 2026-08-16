

INSERT INTO products (
    product_name,
    description,
    unit_price,
    stock_level) VALUES 
    ('Premium Wheat Flour(10kg)', 
    'High-gluten unbleached white wheat flour optimized for commercial bakeries and restaurants', 
    1450.00, 
     3550),

   ('Home Baking Wheat Flour(2kg)', 
    'Sifted grade-one white wheat flour for standard retail packaging and everyday consumption', 
    130.00, 
    1200),

    ('Whole Grain Atta Flour(5kg)', 
     'Stone-ground 100% whole wheat flour packed with fiber, ideal for traditional flatbreads', 
     720.00, 
     3670),


    ('Fortified Cassava Flour(1kg)', 
    'Gluten-free alternative flour enriched with essential vitamins and minerals for health-conscious markets', 
    210.00, 
    8955),

    ('Bulk Bakers Rye Flour(25kg)', 
     'Extra-large industrial sacks of dark rye flour sourced for artisan bread production facilities', 
     3800.00, 
     4575);

INSERT INTO suppliers (
        supplier_name,
        contact_person,
        phone_number,
        email_address) VALUES
        ('Mombasa Grain Millers Ltd', 'John Kamau', '+254711222333', 'info@mombasagrain.co.ke'),
        ( 'Rift Valley Agricultural', 'Sarah Kiprop', '+254722333444', 'supply@riftvalleyagri.com'),
        ('Coast Wholesale Distributors', 'Omar Hassan', '+254733444555', 'orders@coastwholesale.ke'),
        ('Kilimo Bora National Sacks', 'Mary Atieno', '+254701555666', 'sales@kilimobora.org'),
        ('Premium Flour Logistics', 'David Ndwiga', '+254755666777', 'logistics@premiumflour.com');


    
    INSERT INTO purchase_orders (
        order_quantity,
        order_date,
        supplier_id,
        product_id) VALUES
        (250, '2026-08-10', 1, 1),
        (1800, '2026-08-15', 2, 2),
        (2500, '2026-07-09', 3, 3),
        (300, '2026-05-22', 5, 3),
        (800, '2026-06-17', 4, 3),
        (3450, '2026-04-20', 1, 2),
        (200, '2026-06-12', 1, 1),
        (300, '2026-05-28', 5, 4),
        (2350, '2026-04-10', 2, 5),
        (1500, '2026-08-05', 4, 1),
        (650, '2026-02-27', 3, 4),
        (1800, '2026-03-15', 4, 2),
        (3800, '2026-06-13', 5, 3),
        (2200, '2026-04-16', 2, 1),
        (150, '2026-08-02', 1, 5),
        (500, '2026-04-18', 5, 3),
        (450, '2026-03-12', 2, 4),
        (4500, '2026-07-12', 3, 2),
        (150, '2026-03-05', 4, 1);