INSERT INTO customers (name)
SELECT 'Customer ' || x
FROM system_range(1, 100);

INSERT INTO orders (customer_id, amount)
SELECT (RAND() * 99) + 1,
       (RAND() * 1000)
FROM system_range(1, 5000);
