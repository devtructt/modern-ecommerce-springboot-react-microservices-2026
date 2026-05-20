package com.devtructt.ecommerce.commondataservice.dto.response;

import java.util.List;

import com.devtructt.ecommerce.commondataservice.dto.ThreeProductFiltersDto;
import com.devtructt.ecommerce.commondataservice.dto.TwoProductFiltersDto;
import com.devtructt.ecommerce.commondataservice.entity.Apparel;
import com.devtructt.ecommerce.commondataservice.entity.Brand;
import com.devtructt.ecommerce.commondataservice.entity.Gender;

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
public class ProductFilterCombinationResponse {
    List<Gender> genders;
    List<Apparel> apparels;
    List<Brand> brands;
    List<TwoProductFiltersDto> genderApparels;
    List<TwoProductFiltersDto> genderBrands;
    List<TwoProductFiltersDto> apparelBrands;
    List<ThreeProductFiltersDto> genderApparelBrands;
    List<String> productNames;
}
