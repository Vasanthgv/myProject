package com.orderService.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderService.entity.Order;
import com.orderService.repo.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Get all orders with Circuit Breaker protection.
     * If the circuit breaker is OPEN or the method fails, the fallback method is called.
     */
    @CircuitBreaker(name = "orderService", fallbackMethod = "getAllOrdersFallback")
    public List<Order> getAllOrders() {
        return orderRepository.findAll(); // Simulating database interaction
    }

    /**
     * Fallback method for getAllOrders
     */
    public List<Order> getAllOrdersFallback(Throwable throwable) {
        // Return an empty list or some default response when the circuit breaker is triggered
        System.out.println("Circuit Breaker triggered! Returning fallback response.");
        return Collections.emptyList();
    }

    /**
     * Add an order (no Circuit Breaker added here as it's a simple save operation).
     */
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }
}