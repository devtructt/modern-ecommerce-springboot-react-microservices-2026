package com.devtructt.ecommerce.commondataservice.specification;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import com.devtructt.ecommerce.commondataservice.entity.Apparel;
import com.devtructt.ecommerce.commondataservice.entity.Brand;
import com.devtructt.ecommerce.commondataservice.entity.Gender;
import com.devtructt.ecommerce.commondataservice.entity.Product;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductSpecification {
	public static Specification<Product> withGenderIds(Collection<Long> genderIds) {
	    if (CollectionUtils.isEmpty(genderIds)) {
	        return Specification.unrestricted();
	    }
	    return (root, query, criteriaBuilder) -> {
	        Join<Product, Gender> gender = root.join("gender", JoinType.INNER);
	        return gender.get("id").in(genderIds);
	    };
	}
	
	public static Specification<Product> withApparelIds(Collection<Long> apparelIds) {
	    if (CollectionUtils.isEmpty(apparelIds)) {
	        return Specification.unrestricted();
	    }
	    return (root, query, criteriaBuilder) -> {
	        Join<Product, Apparel> apparel = root.join("apparel", JoinType.INNER);
	        return apparel.get("id").in(apparelIds);
	    };
	}
	
	public static Specification<Product> withBrandIds(Collection<Long> brandIds) {
	    if (CollectionUtils.isEmpty(brandIds)) {
	        return Specification.unrestricted();
	    }
	    return (root, query, criteriaBuilder) -> {
	        Join<Product, Brand> brand = root.join("brand", JoinType.INNER);
	        return brand.get("id").in(brandIds);
	    };
	}

	public static Specification<Product> priceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
		if (minPrice == null && maxPrice == null) {
			return Specification.unrestricted();
		}
		return (root, query, criteriaBuilder) -> {
			if (minPrice == null) {
				return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
			}
			if (maxPrice == null) {
				return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
			}
			return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
		};
	}

	public static Specification<Product> nameContains(String productName) {
		if (productName == null || productName.trim().isEmpty()) {
			return Specification.unrestricted();
		}
		return (root, query, criteriaBuilder) -> {
			String pattern = "%" + productName.trim().toLowerCase() + "%";
			return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), pattern);
		};
	}
}
