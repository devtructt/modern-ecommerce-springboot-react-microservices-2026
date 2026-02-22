package com.devtructt.ecommerce.commondataservice.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@ToString(exclude = {"banks"})
@EqualsAndHashCode(exclude = {"banks"})
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String officePhone;

    private String mobilePhone;

    private String alternativePhone;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Bank> banks = new ArrayList<>();

    public Contact(String email, String officePhone, String mobilePhone, String alternativePhone) {
        this.email = email;
        this.officePhone = officePhone;
        this.mobilePhone = mobilePhone;
        this.alternativePhone = alternativePhone;
    }
}