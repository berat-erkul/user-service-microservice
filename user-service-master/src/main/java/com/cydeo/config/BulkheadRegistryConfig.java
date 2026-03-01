package com.cydeo.config;

import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Config server'dan resilience4j.bulkhead.configs.default bind olmadığı için
 * BulkheadRegistry bean'i programatik tanımlanıyor.
 */
@Configuration
public class BulkheadRegistryConfig {

    @Bean
    @ConditionalOnMissingBean
    public BulkheadRegistry bulkheadRegistry() {
        BulkheadConfig defaultConfig = BulkheadConfig.custom()
                .maxConcurrentCalls(5)
                .maxWaitDuration(Duration.ofMillis(2000))
                .build();
        return BulkheadRegistry.of(defaultConfig);
    }
}
