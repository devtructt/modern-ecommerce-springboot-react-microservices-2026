package com.devtructt.ecommerce.commondataservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtructt.ecommerce.commondataservice.entity.SortOption;

public interface SortOptionRepository extends JpaRepository<SortOption, Long> {
    Optional<SortOption> findByName(String name);
}
