# Step 1:

Run the project and see where the N+1 issue comes from.

Explore the database:
http://localhost:8080/h2-console/

# Step 2:

Uncomment the fix and see how/why this solves the N+1 issue.

# Step 3:

Connect to the running database:

http://localhost:8080/h2-console/

Next we can run the final query with EXPLAIN:

    EXPLAIN
    SELECT *
    FROM customers c LEFT JOIN orders o ON o.customer_id = c.id
    WHERE c.id = 42;

What we see (what we don't want to see) is that there is a full table scan:

    SELECT
    "C"."ID",
    "C"."NAME",
    "O"."ID",
    "O"."CUSTOMER_ID",
    "O"."AMOUNT"
    FROM "PUBLIC"."CUSTOMERS" "C"
    /* PUBLIC.PRIMARY_KEY_6: ID = CAST(42 AS BIGINT) */
    /* WHERE C.ID = CAST(42 AS BIGINT)
    */
    LEFT OUTER JOIN "PUBLIC"."ORDERS" "O"
    /* PUBLIC.ORDERS.tableScan */
    ON "O"."CUSTOMER_ID" = "C"."ID"
    WHERE "C"."ID" = CAST(42 AS BIGINT)

To fix this we can add an index to the database, see `data.sql`.

Re-run the EXPLAIN plan and see what the difference is. This way performance issues can be

# Step 4:

Next we can use the power of Spring Data to write a lot more queries. Try and experiment with adding queries to `CustomerRepository`:

    // 1) Simple filter on scalar field
    List<Customer> findByNameStartingWithIgnoreCase(String prefix);

    // 2) Top-N with ordering
    List<Customer> findTop10ByOrderByNameAsc();

    // 3) Traverse relation: customers that have orders above a threshold
    //    -> Spring Data generates a JOIN customers -> orders
    List<Customer> findDistinctByOrdersAmountGreaterThan(BigDecimal minAmount);

    // 4) Combine conditions on root + relation
    List<Customer> findDistinctByNameStartingWithIgnoreCaseAndOrdersAmountBetween(
            String prefix,
            BigDecimal minAmount,
            BigDecimal maxAmount
    );

And also, try to create an `OrderRepository` with queries like:

    // Orders for a given customer id, sorted by amount desc
    List<Order> findByCustomerIdOrderByAmountDesc(Long customerId);

    // Orders for a given customer name (implicit join: order -> customer)
    List<Order> findByCustomerNameIgnoreCase(String name);

    // Same, but limited
    List<Order> findTop20ByCustomerNameIgnoreCaseOrderByAmountDesc(String name);
