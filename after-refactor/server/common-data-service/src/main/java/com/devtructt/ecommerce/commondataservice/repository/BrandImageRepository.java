package com.devtructt.ecommerce.commondataservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtructt.ecommerce.commondataservice.entity.BrandImage;


public interface BrandImageRepository extends JpaRepository<BrandImage, Long> {
	Optional<BrandImage> findByTitle(String title);
}