package com.devtructt.ecommerce.commondataservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@ToString(exclude = {"address", "parentOrder"})
@EqualsAndHashCode(exclude = {"address", "parentOrder"})
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private LocalDateTime createdAt;

    private String deliveryStatus;

    private String trackingLink;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order parentOrder;

    public Order(Long customerId, LocalDateTime createdAt, String deliveryStatus, String trackingLink, Order parentOrder) {
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.deliveryStatus = deliveryStatus;
        this.trackingLink = trackingLink;
        this.parentOrder = parentOrder;
    }
}