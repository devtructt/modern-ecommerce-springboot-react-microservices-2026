package com.devtructt.ecommerce.commondataservice.controller;

import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devtructt.ecommerce.commondataservice.dto.request.ProductFiltersRequest;
import com.devtructt.ecommerce.commondataservice.dto.response.HomepageImagesResponse;
import com.devtructt.ecommerce.commondataservice.dto.response.ProductFiltersByGenderResponse;
import com.devtructt.ecommerce.commondataservice.dto.response.ProductFiltersByOtherProductFiltersResponse;
import com.devtructt.ecommerce.commondataservice.dto.response.ProductsByIdResponse;
import com.devtructt.ecommerce.commondataservice.dto.response.ProductsWithTotalCountResponse;
import com.devtructt.ecommerce.commondataservice.dto.response.ProductFilterCombinationResponse;
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
    public ResponseEntity<ProductsByIdResponse> getProductsByIds(@RequestParam List<Long> ids) {
    	ProductsByIdResponse productsByIdResponse = commonDataService.getProductsByIds(ids);
    	return ResponseEntity.ok(productsByIdResponse);
    }
    
    @PostMapping("/products/search")
    public ResponseEntity<ProductsWithTotalCountResponse> getProductsByProductFilters(@RequestBody ProductFiltersRequest productFiltersRequest) {
    	ProductsWithTotalCountResponse productsWithTotalCountResponse = commonDataService.getProductsByProductFilders(productFiltersRequest);
        return ResponseEntity.ok(productsWithTotalCountResponse);
    }

    @GetMapping("/home-screen-images")
    public ResponseEntity<HomepageImagesResponse> getHomepageImages() {
        HomepageImagesResponse homepageImagesResponse = commonDataService.getHomepageImages();
        return ResponseEntity.ok(homepageImagesResponse);
    }

    @GetMapping("/product-filters/by-gender")
    public ResponseEntity<ProductFiltersByGenderResponse> getProductFiltersByGender() {
        ProductFiltersByGenderResponse productFiltersByGenderResponse = commonDataService.getProductFiltersByGender();
        return ResponseEntity.ok(productFiltersByGenderResponse);
    }

    @PostMapping(value = "/product-filters/by-other-product-filters")
    public ResponseEntity<ProductFiltersByOtherProductFiltersResponse> getProductFiltersByProducts(@RequestBody ProductFiltersRequest productFiltersRequest) {
    	ProductFiltersByOtherProductFiltersResponse productFiltersByProductResponse = commonDataService.getProductFiltersByOtherProductFilters(productFiltersRequest);
        return ResponseEntity.ok(productFiltersByProductResponse);
    }

    @GetMapping("/search-suggestion-list")
    public ResponseEntity<ProductFilterCombinationResponse> getSearchSuggestionList() {
        ProductFilterCombinationResponse searchSuggestionResponse = commonDataService.getSearchSuggestions();
        return ResponseEntity.ok(searchSuggestionResponse);
    }
}