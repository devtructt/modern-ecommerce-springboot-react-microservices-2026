package com.devtructt.ecommerce.commondataservice.dto.response;

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
public class HomeTabsDataResponse {
    private ProductFiltersByGenderDto men;
    private ProductFiltersByGenderDto women;
    private ProductFiltersByGenderDto boys;
    private ProductFiltersByGenderDto girls;
    private ProductFiltersByGenderDto essentials;
    private ProductFiltersByGenderDto homeAndLiving;
}