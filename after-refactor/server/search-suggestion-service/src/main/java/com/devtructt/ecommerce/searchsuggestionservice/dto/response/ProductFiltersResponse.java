package com.devtructt.ecommerce.searchsuggestionservice.dto.response;

import java.util.List;

import com.devtructt.ecommerce.searchsuggestionservice.dto.ProductFilterDto;
import com.devtructt.ecommerce.searchsuggestionservice.dto.ThreeProductFiltersDto;
import com.devtructt.ecommerce.searchsuggestionservice.dto.TwoProductFiltersDto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductFiltersResponse {
    List<ProductFilterDto> genders;
    List<ProductFilterDto> apparels;
    List<ProductFilterDto> brands;
    List<TwoProductFiltersDto> genderApparels;
    List<TwoProductFiltersDto> genderBrands;
    List<TwoProductFiltersDto> apparelBrands;
    List<ThreeProductFiltersDto> genderApparelBrands;
    List<String> productNames;
}
