package com.devtructt.ecommerce.commondataservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devtructt.ecommerce.commondataservice.dto.ProductDto;
import com.devtructt.ecommerce.commondataservice.dto.request.ProductFiltersRequest;
import com.devtructt.ecommerce.commondataservice.dto.response.FilterAttributesResponse;
import com.devtructt.ecommerce.commondataservice.dto.response.HomeScreenImagesResponse;
import com.devtructt.ecommerce.commondataservice.dto.response.HomeTabsDataResponse;
import com.devtructt.ecommerce.commondataservice.dto.response.SearchSuggestionResponse;
import com.devtructt.ecommerce.commondataservice.entity.Product;
import com.devtructt.ecommerce.commondataservice.service.CommonDataService;
import com.devtructt.ecommerce.commondataservice.service.LoadSampleDataService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommonDataController {
    private final Environment environment;
    private final CommonDataService commonDataService;
    private final LoadSampleDataService loadSampleDataService;

    @PostConstruct
    public void loadSampleData() {
        if (environment.acceptsProfiles(Profiles.of("dev"))) {
            loadSampleDataService.loadSampleData();
        }
    }

    @GetMapping(value = "/products")
    public ResponseEntity<?> getProductsByIds(@RequestParam List<Long> ids) {
       	if (CollectionUtils.isEmpty(ids)) {
    		return ResponseEntity.badRequest().body("Query has not followed the required format.");
    	}
    	Map<Long, Product> productById = commonDataService.getProductsByIds(ids);
    	return ResponseEntity.ok(productById);
    }
    
    @PostMapping("/products/search")
    public ResponseEntity<?> getProductsByProductFilters(@RequestBody ProductFiltersRequest productFiltersRequest) {
        ProductDto productDto = commonDataService.getProductsByProductFilders(productFiltersRequest);
//        if (productDto == null) {
//            return ResponseEntity.badRequest().body("Query has not followed the required format.");
//        }
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/home-screen-images")
    public ResponseEntity<HomeScreenImagesResponse> getHomeScreenImages() {
        HomeScreenImagesResponse homeScreenImagesResponse = commonDataService.getHomeScreenImages("homeScreenImages");
        if (homeScreenImagesResponse == null) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(homeScreenImagesResponse);
    }

    @GetMapping("/home-tabs-data")
    public ResponseEntity<HomeTabsDataResponse> getHomeTabsData() {
        HomeTabsDataResponse homeTabsDataResponse = commonDataService.getHomeTabsData("homeTabsData");
        if (homeTabsDataResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(homeTabsDataResponse);
    }

    @GetMapping(value = "/product-filters", params = "query")
    public ResponseEntity<?> getProductFiltersByProducts(@RequestParam("query") String queryParameter) {
        if (!StringUtils.hasText(queryParameter)) {
            return ResponseEntity.badRequest().body("Query parameter is required and cannot be empty.");
        }

        String[] splitParameters = queryParameter.split("=");
        if (splitParameters.length >= 1 && splitParameters[0].equals("productName")) {
            queryParameter = "productFilters=all";
        }

        FilterAttributesResponse result = commonDataService.getProductFiltersByProducts(queryParameter);

        if (result == null) {
            return ResponseEntity.badRequest().body("Query has not followed the required format.");
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/search-suggestion-list")
    public ResponseEntity<SearchSuggestionResponse> getSearchSuggestionList() {
        SearchSuggestionResponse searchSuggestionResponse = commonDataService.getSearchSuggestionList();
        if (searchSuggestionResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(searchSuggestionResponse);
    }
}