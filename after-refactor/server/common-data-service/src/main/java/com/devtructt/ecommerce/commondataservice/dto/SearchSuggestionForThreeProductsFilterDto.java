package com.devtructt.ecommerce.commondataservice.dto;

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
public class SearchSuggestionForThreeProductsFilterDto {
    Integer firstProductFilterId;
    String firstProductFilterName;
    Integer secondProductFilterId;
    String secondProductFilterName;
    Integer thirdProductFilterId;
    String thirdProductFilterName;
}