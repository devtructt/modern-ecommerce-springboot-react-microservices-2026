package com.devtructt.ecommerce.commondataservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtructt.ecommerce.commondataservice.entity.PriceRange;

public interface PriceRangeRepository extends JpaRepository<PriceRange, Long> {
    Optional<PriceRange> findByName(String name);
}
