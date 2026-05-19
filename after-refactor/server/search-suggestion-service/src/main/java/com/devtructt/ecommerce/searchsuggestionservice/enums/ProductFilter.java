package com.devtructt.ecommerce.searchsuggestionservice.enums;

public enum ProductFilter {
	GENDER("genderIds"),
    APPAREL("apparelIds"),
    BRAND("brandIds");

    private final String requestParameter;

    private ProductFilter(String requestParameter) {
        this.requestParameter = requestParameter;
    }

    public String getValue() {
        return requestParameter;
    }
}
