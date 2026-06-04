package com.devtructt.ecommerce.commondataservice.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import com.devtructt.ecommerce.commondataservice.service.LoadSampleDataService;

import lombok.RequiredArgsConstructor;

//@Component
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {
    private final Environment environment;
    private final LoadSampleDataService loadSampleDataService;

    @Override
    public void run(String... args) {
    	if (environment.acceptsProfiles(Profiles.of("dev", "default"))) {
            loadSampleDataService.loadSampleData();
        }
    }
}