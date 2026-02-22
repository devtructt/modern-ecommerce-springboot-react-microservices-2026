package com.devtructt.ecommerce.commondataservice.dto.response;

import java.util.List;

import com.devtructt.ecommerce.commondataservice.dto.ProductFilterDto;

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
public class FilterAttributesResponse {
	private List<ProductFilterDto> genders;
	private List<ProductFilterDto> apparels;
    private List<ProductFilterDto> brands;
    private List<ProductFilterDto> priceRanges;
    private List<ProductFilterDto> sortOptions;
}
