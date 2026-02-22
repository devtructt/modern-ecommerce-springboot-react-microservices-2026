package com.devtructt.ecommerce.commondataservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtructt.ecommerce.commondataservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
