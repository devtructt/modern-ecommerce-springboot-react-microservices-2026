package com.devtructt.ecommerce.commondataservice.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@ToString(exclude = {"banks", "order"})
@EqualsAndHashCode(exclude = {"banks", "order"})
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String streetLine1;

    private String streetLine2;

    private String postalCode;

    private String state;

    private String country;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Bank> banks = new ArrayList<>();

    @OneToOne(mappedBy = "address")
    private Order order;

    public Address(String streetLine1, String streetLine2, String postalCode, String state, String country) {
        this.streetLine1 = streetLine1;
        this.streetLine2 = streetLine2;
        this.postalCode = postalCode;
        this.state = state;
        this.country = country;
    }
}