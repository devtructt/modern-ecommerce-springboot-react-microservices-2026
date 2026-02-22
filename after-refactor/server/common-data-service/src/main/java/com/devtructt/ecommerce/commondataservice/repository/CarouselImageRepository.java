package com.devtructt.ecommerce.commondataservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtructt.ecommerce.commondataservice.entity.CarouselImage;


public interface CarouselImageRepository extends JpaRepository<CarouselImage, Long> {
	Optional<CarouselImage> findByTitle(String title);
}