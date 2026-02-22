package com.devtructt.ecommerce.searchsuggestionservice.service;

import java.util.List;

import com.devtructt.ecommerce.searchsuggestionservice.dto.SearchSuggestionDto;


public interface SearchSuggestionService {
	void loadSearchSuggestions();

    List<SearchSuggestionDto> getSearchSuggestions(String keyword);

    List<SearchSuggestionDto> getDefaultSuggestions();
}
