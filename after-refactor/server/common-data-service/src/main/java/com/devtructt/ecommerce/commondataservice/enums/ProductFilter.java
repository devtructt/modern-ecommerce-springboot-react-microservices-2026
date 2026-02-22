package com.devtructt.ecommerce.commondataservice.enums;

public enum ProductFilter {
    GENDER("genders="),
    APPAREL("apparels="),
    BRAND("brands="),
    PRICE_RANGE("price="),
    SORT_OPTION("sort=");

    private final String queryParam;

    ProductFilter(String queryParam) {
        this.queryParam = queryParam;
    }

    public String getQueryParam() {
        return queryParam;
    }
}