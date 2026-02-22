package com.devtructt.ecommerce.commondataservice.dto;

import java.util.List;

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
public class ProductFiltersByGenderDto {
    private List<ProductFilterDto> brands;
    private List<ProductFilterDto> apparels;
}