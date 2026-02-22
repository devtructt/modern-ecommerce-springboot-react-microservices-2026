package com.devtructt.ecommerce.commondataservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtructt.ecommerce.commondataservice.entity.Gender;

public interface GenderRepository extends JpaRepository<Gender, Long> {
    Optional<Gender> findByName(String name);
}
