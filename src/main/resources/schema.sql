DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customers;

CREATE TABLE customers (
                           id   BIGSERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL
);

CREATE TABLE orders (
                        id           BIGSERIAL PRIMARY KEY,
                        customer_id  BIGINT NOT NULL,
                        amount       NUMERIC(10,2) NOT NULL
);

/* If you've noticed performance issues... perhaps add an index? */
/*
   CREATE INDEX idx_orders_customer ON orders(customer_id);
*/
CREATE INDEX idx_orders_customer ON orders(customer_id);
