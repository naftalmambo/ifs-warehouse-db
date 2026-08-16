
-- REPORT : Inventory reconciliation and variance audit analysis
-- This query links live shelf balances with historical purchase order logs using relational inner joins and aggregations.
-- It acts as a warehouse audit tool to track total inbound volume per product and detect data discrepancies or stock leakage.


SELECT 
    products.product_name,
    products.stock_level,
    SUM(purchase_orders.order_quantity) AS total_ordered
FROM 
    purchase_orders
INNER JOIN products 
    ON purchase_orders.product_id = products.product_id
GROUP BY 
    products.product_name, 
    products.stock_level
ORDER BY 
    total_ordered DESC;
