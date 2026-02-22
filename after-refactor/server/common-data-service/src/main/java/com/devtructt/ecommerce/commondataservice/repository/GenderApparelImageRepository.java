package com.devtructt.ecommerce.commondataservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtructt.ecommerce.commondataservice.entity.GenderApparelImage;


public interface GenderApparelImageRepository extends JpaRepository<GenderApparelImage, Long> {
	Optional<GenderApparelImage> findByTitle(String title);
}
