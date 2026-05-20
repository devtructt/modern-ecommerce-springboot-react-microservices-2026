package com.devtructt.ecommerce.commondataservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductFilter {
    GENDER("genders="),
    APPAREL("apparels="),
    BRAND("brands="),
    PRICE_RANGE("price="),
    SORT_OPTION("sort=");

    private final String queryParam;
}