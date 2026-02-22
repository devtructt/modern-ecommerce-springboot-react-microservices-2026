package com.devtructt.ecommerce.searchsuggestionservice.service.impl;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.devtructt.ecommerce.searchsuggestionservice.constant.AttributeType;
import com.devtructt.ecommerce.searchsuggestionservice.constant.SearchSuggestionConstants;
import com.devtructt.ecommerce.searchsuggestionservice.dto.SearchSuggestionDto;
import com.devtructt.ecommerce.searchsuggestionservice.dto.response.SearchSuggestionResponseDto;
import com.devtructt.ecommerce.searchsuggestionservice.dto.response.SearchSuggestionResponseDto.AttributeKeywordDto;
import com.devtructt.ecommerce.searchsuggestionservice.dto.response.SearchSuggestionResponseDto.CombinationKeywordDto;
import com.devtructt.ecommerce.searchsuggestionservice.dto.response.SearchSuggestionResponseDto.ThreeAttributeKeywordDto;
import com.devtructt.ecommerce.searchsuggestionservice.service.SearchSuggestionService;
import com.devtructt.ecommerce.searchsuggestionservice.util.PermutationGenerator;

import lombok.RequiredArgsConstructor;
import reactor.util.retry.Retry;

@Service
@RequiredArgsConstructor
public class SearchSuggestionServiceImpl implements SearchSuggestionService {
    private final WebClient webClient;

    @Value("${services.common-data.endpoints.search-suggestion-list}")
    private String searchSuggestionsUrl;

    private Map<String, List<SearchSuggestionDto>> prefixToKeywordSuggestionsMap = new HashMap<>();
    
    private List<SearchSuggestionDto> defaultSearchSuggestions = new ArrayList<>();

    public void loadSearchSuggestions() {
        webClient.get()
                .uri(URI.create(searchSuggestionsUrl))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SearchSuggestionResponseDto.class)
                .timeout(Duration.ofSeconds(10))
                .retryWhen(Retry.backoff(5, Duration.ofMillis(5000))
                        .filter(throwable -> throwable instanceof IOException || throwable instanceof InterruptedException))
                .subscribe(
                        this::processResponse,
                        this::handleLoadError
                );
    }
    
    private void processResponse(SearchSuggestionResponseDto response) {
        processAttributeKeywords(response.getGenderKeywords(), AttributeType.GENDERS);
        processAttributeKeywords(response.getApparelKeywords(), AttributeType.APPARELS);
        processAttributeKeywords(response.getBrandKeywords(), AttributeType.BRANDS);
        processProductKeywords(response.getProductKeywords());
        processTwoAttributeKeywords(response.getGenderApparelKeywords(), Arrays.asList(AttributeType.GENDERS, AttributeType.APPARELS));
        processTwoAttributeKeywords(response.getGenderBrandKeywords(), Arrays.asList(AttributeType.GENDERS, AttributeType.BRANDS));
        processTwoAttributeKeywords(response.getApparelBrandKeywords(), Arrays.asList(AttributeType.APPARELS, AttributeType.BRANDS));
        processThreeAttributeKeywords(response.getThreeAttributeKeywords(), Arrays.asList(AttributeType.GENDERS, AttributeType.APPARELS, AttributeType.BRANDS));
    }

    private void handleLoadError(Throwable error) {
        prefixToKeywordSuggestionsMap.clear();
        defaultSearchSuggestions.clear();
    }

	private void processAttributeKeywords(List<AttributeKeywordDto> keywords, AttributeType attributeType) {
		AtomicInteger count = new AtomicInteger(0);
		keywords.stream().forEach(keyword -> {
			String searchSuggestionLink = attributeType.getValue() + "=" + keyword.getAttributeId();
			Integer searchSuggestionRank = SearchSuggestionConstants.DEFAULT_SEARCH_SUGGESTION_RANK;
			addSearchSuggestion(keyword.getType(), searchSuggestionLink, searchSuggestionRank);
			if (AttributeType.APPARELS.equals(attributeType) && count.get() < SearchSuggestionConstants.DEFAULT_SEARCH_SUGGESTION_LIMIT) {
				defaultSearchSuggestions.add(new SearchSuggestionDto(keyword.getType(), searchSuggestionLink, searchSuggestionRank));
				count.getAndIncrement();
			}
		});
	}

	private void processProductKeywords(List<String> productKeywords) {
		productKeywords.forEach(productKeyword -> {
			String searchSuggestionLink = SearchSuggestionConstants.PRODUCT_SEARCH_SUGGESTION_LINK_PREFIX + productKeyword;
			Integer searchSuggestionRank = SearchSuggestionConstants.DEFAULT_SEARCH_SUGGESTION_RANK;
			addSearchSuggestion(productKeyword, searchSuggestionLink, searchSuggestionRank);
		});
	}

    private void processTwoAttributeKeywords(List<CombinationKeywordDto> twoAttributeKeywords, List<AttributeType> attributeTypes) {
    	twoAttributeKeywords.forEach(twoAttributeKeyword -> {
            String firstKeyword = twoAttributeKeyword.getFirstAttributeId();
            String secondKeyword = twoAttributeKeyword.getSecondAttributeId();
            String[] keywords = {firstKeyword, secondKeyword};
            String searchSuggestionLink = attributeTypes.get(0).getValue() + "=" + twoAttributeKeyword.getFirstAttributeId() +
            		SearchSuggestionConstants.ATTRIBUTE_LINK_DELIMITER + attributeTypes.get(1).getValue() + "=" + twoAttributeKeyword.getSecondAttributeId();
            Integer searchSuggestionRank = SearchSuggestionConstants.DEFAULT_SEARCH_SUGGESTION_RANK;
            new PermutationGenerator(keywords).getPermutations().forEach(permutedKeyword -> {
            	addSearchSuggestion(permutedKeyword, searchSuggestionLink, searchSuggestionRank);
            });
        });
    }

    private void processThreeAttributeKeywords(List<ThreeAttributeKeywordDto> threeAttributeKeywords, List<AttributeType> attributeTypes) {
        threeAttributeKeywords.forEach(threeAttributeKeyword -> {
            String firstKeyword = threeAttributeKeyword.getFirstAttributeId();
            String secondKeyword = threeAttributeKeyword.getSecondAttributeId();
            String thirdKeyword = threeAttributeKeyword.getThirdAttributeType();
            String[] keywords = {firstKeyword, secondKeyword, thirdKeyword};
            String suggestionLink = attributeTypes.get(0).getValue() + "=" + threeAttributeKeyword.getFirstAttributeId() +
            		SearchSuggestionConstants.ATTRIBUTE_LINK_DELIMITER + attributeTypes.get(1).getValue() + "=" + threeAttributeKeyword.getSecondAttributeId() +
            		SearchSuggestionConstants.ATTRIBUTE_LINK_DELIMITER + attributeTypes.get(2).getValue() + "=" + threeAttributeKeyword.getThirdAttributeType();
            Integer searchSuggestionRank = SearchSuggestionConstants.DEFAULT_SEARCH_SUGGESTION_RANK;
            new PermutationGenerator(keywords).getPermutations().forEach(permutedKeyword -> {
            	addSearchSuggestion(permutedKeyword, suggestionLink, searchSuggestionRank);
            });
        });
    }
    
	private void addSearchSuggestion(String keyword, String searchSuggestionLink, Integer searchSuggestionRank) {
		Stream.iterate(1, index -> index <= keyword.length(), index -> index + 1)
				.map(index -> keyword.substring(0, index).toLowerCase())
				.forEach(prefix -> {
					prefixToKeywordSuggestionsMap.computeIfAbsent(prefix, ignored -> new ArrayList<>())
							.add(new SearchSuggestionDto(keyword, searchSuggestionLink, searchSuggestionRank));
				});
	}

    public List<SearchSuggestionDto> getSearchSuggestions(String keyword) {
        return Stream.iterate(keyword.length(), index -> index > 0, index -> index - 1)
                .map(index -> keyword.substring(0, index).toLowerCase())
                .map(prefixToKeywordSuggestionsMap::get)
                .findFirst()
                .orElseGet(Collections::emptyList);
    }

    public List<SearchSuggestionDto> getDefaultSuggestions() {
        return Collections.unmodifiableList(defaultSearchSuggestions);
    }
}