package com.devtructt.ecommerce.commondataservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtructt.ecommerce.commondataservice.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
