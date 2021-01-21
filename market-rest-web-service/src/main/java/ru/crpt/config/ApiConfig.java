package ru.crpt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.crpt.cache.Cache;
import ru.crpt.model.Document;

@Configuration
public class ApiConfig {
    @Bean
    public Cache<String, Document> evaluatingCache() {
        return new Cache<>();
    }
}
