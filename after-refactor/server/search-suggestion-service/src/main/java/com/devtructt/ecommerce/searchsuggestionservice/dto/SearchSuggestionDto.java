package com.devtructt.ecommerce.searchsuggestionservice.dto;

import lombok.Value;

@Value
public class SearchSuggestionDto {
    String keyword;
    String link;
    Integer rank;
}