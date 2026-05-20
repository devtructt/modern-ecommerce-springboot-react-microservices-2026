package com.devtructt.ecommerce.commondataservice.dto.response;

import java.util.List;

import com.devtructt.ecommerce.commondataservice.dto.ProductFiltersByGenderDto;

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
public class ProductFiltersByGenderResponse {
	private List<ProductFiltersByGenderDto> productFilters;
}
