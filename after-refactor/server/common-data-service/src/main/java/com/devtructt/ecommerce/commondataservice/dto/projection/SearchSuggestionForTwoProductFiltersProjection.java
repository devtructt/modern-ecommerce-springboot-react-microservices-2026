package com.devtructt.ecommerce.commondataservice.dto.projection;

public interface SearchSuggestionForTwoProductFiltersProjection {
    Integer getFirstProductFilterId();
    String getFirstProductFilterName();
    Integer getSecondProductFilterId();
    String getSecondProductFilterName();
}
