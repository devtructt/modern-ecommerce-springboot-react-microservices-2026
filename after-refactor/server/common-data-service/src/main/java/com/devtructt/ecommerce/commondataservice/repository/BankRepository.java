package com.devtructt.ecommerce.commondataservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtructt.ecommerce.commondataservice.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
