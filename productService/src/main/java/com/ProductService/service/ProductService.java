package com.ProductService.service;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProductService.entity.Product;
import com.ProductService.repo.ProductRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Fetch all products with Circuit Breaker protection.
     * If the Circuit Breaker is triggered, the fallback method is called.
     */
    @CircuitBreaker(name = "productService", fallbackMethod = "getAllProductsFallback")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Fallback method for getAllProducts when Circuit Breaker is triggered.
     */
    public List<Product> getAllProductsFallback(Throwable throwable) {
        System.out.println("Circuit Breaker triggered! Returning fallback response.");
        return Collections.emptyList(); // Return an empty list as a fallback
    }

    /**
     * Add a product (no Circuit Breaker added here as it's a simple save operation).
     */
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
}