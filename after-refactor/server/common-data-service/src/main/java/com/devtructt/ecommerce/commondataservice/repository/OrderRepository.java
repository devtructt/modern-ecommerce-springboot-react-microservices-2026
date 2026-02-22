package com.devtructt.ecommerce.commondataservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtructt.ecommerce.commondataservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
