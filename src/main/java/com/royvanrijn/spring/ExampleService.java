package com.royvanrijn.spring;

import java.util.List;

import com.royvanrijn.spring.entities.Customer;
import com.royvanrijn.spring.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    private final CustomerRepository customerRepository;

    public ExampleService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void run() {

        System.out.println("=== 1) N+1 with findAll() + lazy orders ===");
        List<Customer> customers = customerRepository.findAll();

        customers.stream().limit(5).forEach(c -> {
            int count = c.getOrders().size(); // N+1 but works, session open
            System.out.println(c.getName() + " -> " + count + " orders");
        });

//        System.out.println("\n=== 2) Single query with EntityGraph ===");
//        List<Customer> customersWithOrders = customerRepository.findAllBy();
//
//        customersWithOrders.stream().limit(5).forEach(c -> {
//            int count = c.getOrders().size(); // no extra queries, still same Tx
//            System.out.println(c.getName() + " -> " + count + " orders");
//        });
    }
}
