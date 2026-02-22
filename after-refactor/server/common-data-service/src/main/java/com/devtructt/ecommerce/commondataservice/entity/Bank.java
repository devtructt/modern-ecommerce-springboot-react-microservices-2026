package com.devtructt.ecommerce.commondataservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"address", "contact"})
@EqualsAndHashCode(exclude = {"address", "contact"})
@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private Long sellerId;

    private String firstName;

    private String lastName;

    private String bankName;

    private String routingNumber;

    private String accountNumber;

    @ManyToOne
    private Address address;

    @ManyToOne
    private Contact contact;

    public Bank(Long customerId, Long sellerId, String firstName, String lastName, String bankName, String routingNumber, String accountNumber) {
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bankName = bankName;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
    }
}