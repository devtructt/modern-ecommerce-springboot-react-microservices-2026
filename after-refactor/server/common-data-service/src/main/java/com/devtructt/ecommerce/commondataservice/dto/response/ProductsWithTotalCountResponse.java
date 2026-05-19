package com.devtructt.ecommerce.commondataservice.dto.response;

import java.util.List;

import com.devtructt.ecommerce.commondataservice.entity.Product;

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
@ToString
@EqualsAndHashCode
public class ProductsWithTotalCountResponse {
	private Long totalCount;
	private List<Product> products;
}