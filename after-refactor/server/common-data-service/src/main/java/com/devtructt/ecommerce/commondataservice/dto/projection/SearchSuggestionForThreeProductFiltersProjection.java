package com.devtructt.ecommerce.commondataservice.dto.projection;

public interface SearchSuggestionForThreeProductFiltersProjection {
    Integer getFirstProductFilterId();
    String getFirstProductFilterName();
    Integer getSecondProductFilterId();
    String getSecondProductFilterName();
    Integer getThirdProductFilterId();
    String getThirdProductFilterName();
}
