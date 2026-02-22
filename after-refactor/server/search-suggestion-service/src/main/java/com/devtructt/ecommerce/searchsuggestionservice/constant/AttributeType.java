package com.devtructt.ecommerce.searchsuggestionservice.constant;

public enum AttributeType {
	GENDERS("genders"),
    APPARELS("apparels"),
    BRANDS("brands");

    private final String value;

    private AttributeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
