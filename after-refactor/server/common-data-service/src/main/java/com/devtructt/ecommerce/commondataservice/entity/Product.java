package com.devtructt.ecommerce.commondataservice.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = { "gender", "apparel", "brand", "priceRange", "orders" })
@EqualsAndHashCode(exclude = { "gender", "apparel", "brand", "priceRange", "orders" })
@Entity
@Table(indexes = { @Index(columnList = "gender_id, apparel_id, brand_id, price") })
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private Long sellerId;

	private LocalDate publicationDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Gender gender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Apparel apparel;

	@ManyToOne
	@JsonIgnore
	private Brand brand;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private PriceRange priceRange;

	private BigDecimal price;

	private Integer availableQuantity;

	private Integer deliveryTime;

	private BigDecimal rating;

	private Boolean verificationStatus;

	private String imageUrl;
	
	private String localImagePath;


	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	@Builder.Default
	private List<Order> orders = new ArrayList<>();

	public Product(Long sellerId, String name, LocalDate publicationDate, Gender gender, Apparel apparel, Brand brand,
			PriceRange priceRange, BigDecimal price, Integer availableQuantity, Integer deliveryTime, BigDecimal rating,
			Boolean verificationStatus, String localImagePath, String imageUrl) {
		this.sellerId = sellerId;
		this.name = name;
		this.publicationDate = publicationDate;
		this.gender = gender;
		this.apparel = apparel;
		this.brand = brand;
		this.priceRange = priceRange;
		this.price = price;
		this.availableQuantity = availableQuantity;
		this.deliveryTime = deliveryTime;
		this.rating = rating;
		this.verificationStatus = verificationStatus;
		this.localImagePath = localImagePath;
		this.imageUrl = imageUrl;
	}
}