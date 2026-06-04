package com.devtructt.ecommerce.searchsuggestionservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	@Value("${services.common-data.base-url}")
    private String baseUrl;
	
    @Bean
    WebClient productServiceWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
