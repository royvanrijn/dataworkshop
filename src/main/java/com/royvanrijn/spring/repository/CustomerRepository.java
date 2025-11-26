package com.royvanrijn.spring.repository;

import com.royvanrijn.spring.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

//    @EntityGraph(attributePaths = "orders")
//    List<Customer> findAllBy(); // single query with join
}
