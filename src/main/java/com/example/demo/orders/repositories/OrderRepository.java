package com.example.demo.orders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.orders.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
