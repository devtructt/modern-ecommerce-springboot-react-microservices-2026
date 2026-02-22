package com.devtructt.ecommerce.commondataservice.dto.response;

import java.util.List;

import com.devtructt.ecommerce.commondataservice.dto.SearchSuggestionForThreeProductsFilterDto;
import com.devtructt.ecommerce.commondataservice.dto.SearchSuggestionForTwoProductFiltersDto;
import com.devtructt.ecommerce.commondataservice.entity.Apparel;
import com.devtructt.ecommerce.commondataservice.entity.Brand;
import com.devtructt.ecommerce.commondataservice.entity.Gender;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SearchSuggestionResponse {
    List<Gender> genderKeywords;
    List<Apparel> apparelKeywords;
    List<Brand> brandKeywords;
    List<SearchSuggestionForTwoProductFiltersDto> genderApparelKeywords;
    List<SearchSuggestionForTwoProductFiltersDto> genderBrandKeywords;
    List<SearchSuggestionForTwoProductFiltersDto> apparelBrandKeywords;
    List<SearchSuggestionForThreeProductsFilterDto> genderApparelBrandKeywords;
    List<String> productKeywords;
}
