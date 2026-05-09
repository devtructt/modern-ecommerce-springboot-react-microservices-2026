package com.devtructt.ecommerce.commondataservice.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SortOption {
	@JsonProperty("newest")
	NEWEST,

	@JsonProperty("popularity")
	POPULARITY,

	@JsonProperty("priceLowToHigh")
	PRICE_LOW_TO_HIGH,

	@JsonProperty("priceHighToLow")
	PRICE_HIGH_TO_LOW;
}