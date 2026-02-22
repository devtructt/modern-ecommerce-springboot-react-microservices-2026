package com.devtructt.ecommerce.commondataservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtructt.ecommerce.commondataservice.entity.Brand;


public interface BrandRepository extends JpaRepository<Brand, Long> {
	Optional<Brand> findByName(String name);
}
