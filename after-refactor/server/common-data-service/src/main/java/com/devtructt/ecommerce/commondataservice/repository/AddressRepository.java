package com.devtructt.ecommerce.commondataservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtructt.ecommerce.commondataservice.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}