package com.devtructt.ecommerce.searchsuggestionservice.service;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.devtructt.ecommerce.searchsuggestionservice.constant.SearchSuggestionConstants;
import com.devtructt.ecommerce.searchsuggestionservice.dto.ProductFilterDto;
import com.devtructt.ecommerce.searchsuggestionservice.dto.ThreeProductFiltersDto;
import com.devtructt.ecommerce.searchsuggestionservice.dto.TwoProductFiltersDto;
import com.devtructt.ecommerce.searchsuggestionservice.dto.response.ProductFiltersResponse;
import com.devtructt.ecommerce.searchsuggestionservice.dto.response.SearchSuggestionResponse;
import com.devtructt.ecommerce.searchsuggestionservice.enums.ProductFilter;
import com.devtructt.ecommerce.searchsuggestionservice.util.PermutationGeneratorUtil;

import lombok.RequiredArgsConstructor;
import reactor.util.retry.Retry;

@Service
@RequiredArgsConstructor
public class SearchSuggestionService {
    private final WebClient webClient;
    private final PermutationGeneratorUtil permutationGeneratorUtil;

    @Value("${services.common-data.endpoints.product-filters-combinations}")
    private String searchSuggestionsUrl;

    private Map<String, List<SearchSuggestionResponse>> keywordSuggestionsByPrefix = new HashMap<>();
    
    private List<SearchSuggestionResponse> defaultSearchSuggestions = new ArrayList<>();

    public void loadSearchSuggestions() {
        webClient.get()
                .uri(URI.create(searchSuggestionsUrl))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ProductFiltersResponse.class)
                .timeout(Duration.ofSeconds(10))
                .retryWhen(Retry
                		.backoff(5, Duration.ofMillis(5000))
                        .filter(throwable -> throwable instanceof IOException || throwable instanceof InterruptedException))
                .subscribe(
                        this::processResponse,
                        this::handleError
                );
    }
    
    private void processResponse(ProductFiltersResponse response) {
        processProductFilterKeywords(response.getGenders(), ProductFilter.GENDER);
        processProductFilterKeywords(response.getApparels(), ProductFilter.APPAREL);
        processProductFilterKeywords(response.getBrands(), ProductFilter.BRAND);
        processTwoProductFiltersKeywords(response.getGenderApparels(), Arrays.asList(ProductFilter.GENDER, ProductFilter.APPAREL));
        processTwoProductFiltersKeywords(response.getGenderBrands(), Arrays.asList(ProductFilter.GENDER, ProductFilter.BRAND));
        processTwoProductFiltersKeywords(response.getApparelBrands(), Arrays.asList(ProductFilter.APPAREL, ProductFilter.BRAND));
        processThreeProductFiltersKeywords(response.getGenderApparelBrands(), Arrays.asList(ProductFilter.GENDER, ProductFilter.APPAREL, ProductFilter.BRAND));
        processProductKeywords(response.getProductNames());
    }

    private void handleError(Throwable error) {
        keywordSuggestionsByPrefix.clear();
        defaultSearchSuggestions.clear();
    }

	private void processProductFilterKeywords(List<ProductFilterDto> productFilterDtos, ProductFilter productFilter) {
		AtomicInteger count = new AtomicInteger(0);
		
		productFilterDtos.stream().forEach(productFilterDto -> {
			String searchSuggestionKeyword = productFilterDto.getName();
			String searchSuggestionLink = productFilter.getValue() + "=" + productFilterDto.getId();
			Integer searchSuggestionRank = SearchSuggestionConstants.DEFAULT_SEARCH_SUGGESTION_RANK;
			addSearchSuggestion(searchSuggestionKeyword, searchSuggestionLink, searchSuggestionRank);
			
			if (ProductFilter.APPAREL.equals(productFilter) && count.get() < SearchSuggestionConstants.DEFAULT_SEARCH_SUGGESTION_LIMIT) {
				defaultSearchSuggestions.add(new SearchSuggestionResponse(searchSuggestionKeyword, searchSuggestionLink, searchSuggestionRank));
				count.getAndIncrement();
			}
		});
	}

    private void processTwoProductFiltersKeywords(List<TwoProductFiltersDto> twoProductFiltersDtos, List<ProductFilter> productFilters) {
    	twoProductFiltersDtos.forEach(twoProductFiltersDto -> {
            String firstProductFilterName = twoProductFiltersDto.getFirstProductFilterName();
            String secondProductFilterName = twoProductFiltersDto.getSecondProductFilterName();
            
            String[] searchSuggestionKeywords = {firstProductFilterName, secondProductFilterName};
            String searchSuggestionLink = productFilters.get(0).getValue() + "=" + twoProductFiltersDto.getFirstProductFilterId() + SearchSuggestionConstants.ATTRIBUTE_LINK_DELIMITER + 
            		productFilters.get(1).getValue() + "=" + twoProductFiltersDto.getSecondProductFilterId();
            Integer searchSuggestionRank = SearchSuggestionConstants.DEFAULT_SEARCH_SUGGESTION_RANK;
            
            permutationGeneratorUtil.generate(searchSuggestionKeywords).forEach(searchSuggestionKeyword -> {
            	addSearchSuggestion(searchSuggestionKeyword, searchSuggestionLink, searchSuggestionRank);
            });
        });
    }

    private void processThreeProductFiltersKeywords(List<ThreeProductFiltersDto> threeProductFiltersDtos, List<ProductFilter> productFilters) {
    	threeProductFiltersDtos.forEach(threeProductFiltersDto -> {
            String firstProductFilterName = threeProductFiltersDto.getFirstProductFilterName();
            String secondProductFilterName = threeProductFiltersDto.getSecondProductFilterName();
            String thirdProductFilterName = threeProductFiltersDto.getThirdProductFilterName();
            
            String[] searchSuggestionKeywords = {firstProductFilterName, secondProductFilterName, thirdProductFilterName};
            String searchSuggestionLink = productFilters.get(0).getValue() + "=" + threeProductFiltersDto.getFirstProductFilterId() + SearchSuggestionConstants.ATTRIBUTE_LINK_DELIMITER + 
            		productFilters.get(1).getValue() + "=" + threeProductFiltersDto.getSecondProductFilterId() + SearchSuggestionConstants.ATTRIBUTE_LINK_DELIMITER + 
            		productFilters.get(2).getValue() + "=" + threeProductFiltersDto.getThirdProductFilterId();
            Integer searchSuggestionRank = SearchSuggestionConstants.DEFAULT_SEARCH_SUGGESTION_RANK;
            
            permutationGeneratorUtil.generate(searchSuggestionKeywords).forEach(searchSuggestionKeyword -> {
            	addSearchSuggestion(searchSuggestionKeyword, searchSuggestionLink, searchSuggestionRank);
            });
        });
    }
    
	private void processProductKeywords(List<String> productKeywords) {
		productKeywords.forEach(productKeyword -> {
			String searchSuggestionLink = SearchSuggestionConstants.PRODUCT_SEARCH_SUGGESTION_LINK_PREFIX + productKeyword;
			Integer searchSuggestionRank = SearchSuggestionConstants.DEFAULT_SEARCH_SUGGESTION_RANK;
			addSearchSuggestion(productKeyword, searchSuggestionLink, searchSuggestionRank);
		});
	}
    
	private void addSearchSuggestion(String searchSuggestionKeyword, String searchSuggestionLink, Integer searchSuggestionRank) {
		Stream.iterate(1, index -> index <= searchSuggestionKeyword.length(), index -> index + 1)
				.map(index -> searchSuggestionKeyword.substring(0, index).toLowerCase())
				.forEach(prefix -> {
					keywordSuggestionsByPrefix.computeIfAbsent(prefix, ignored -> new ArrayList<>())
							.add(new SearchSuggestionResponse(searchSuggestionKeyword, searchSuggestionLink, searchSuggestionRank));
				});
	}

    public List<SearchSuggestionResponse> getSearchSuggestions(String keyword) {
        for (int i = keyword.length(); i > 0; i--) {
            List<SearchSuggestionResponse> result = keywordSuggestionsByPrefix.get(keyword.substring(0, i).toLowerCase());
            if (result != null) {
                return result;
            }
        }
        return List.of();
    }

    public List<SearchSuggestionResponse> getDefaultSearchSuggestions() {
        return defaultSearchSuggestions;
    }
}