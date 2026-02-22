package com.devtructt.ecommerce.searchsuggestionservice.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.devtructt.ecommerce.searchsuggestionservice.service.SearchSuggestionService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {
    private final SearchSuggestionService searchSuggestionService;

    @Override
    public void run(String... args) {
        searchSuggestionService.loadSearchSuggestions();
    }
}