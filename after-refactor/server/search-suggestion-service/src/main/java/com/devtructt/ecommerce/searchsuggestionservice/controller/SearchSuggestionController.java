package com.devtructt.ecommerce.searchsuggestionservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devtructt.ecommerce.searchsuggestionservice.dto.SearchSuggestionDto;
import com.devtructt.ecommerce.searchsuggestionservice.service.SearchSuggestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SearchSuggestionController {
    private final SearchSuggestionService searchSuggestionService;

    @GetMapping("/search-suggestions")
    public ResponseEntity<List<SearchSuggestionDto>> searchSuggestions(@RequestParam String keyword) {
        return ResponseEntity.ok(searchSuggestionService.getSearchSuggestions(keyword));
    }

    @GetMapping("/default-search-suggestions")
    public ResponseEntity<List<SearchSuggestionDto>> getDefaultSuggestions() {
        return ResponseEntity.ok(searchSuggestionService.getDefaultSuggestions());
    }
}