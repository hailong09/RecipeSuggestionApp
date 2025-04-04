package com.RecipeSuggestion.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:.env")
public class DotEnvConfig {
    // No additional code needed
}
