package com.devtructt.ecommerce.commondataservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtructt.ecommerce.commondataservice.entity.Apparel;

public interface ApparelRepository extends JpaRepository<Apparel, Long> {
	Optional<Apparel> findByName(String name);
}
