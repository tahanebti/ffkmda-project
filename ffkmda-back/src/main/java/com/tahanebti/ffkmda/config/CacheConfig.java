package com.tahanebti.ffkmda.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CacheConfig {
    
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("clubsByAddress");
    }
    
    
    
    @Primary
    @Bean("customKeyGenerator")
    public KeyGenerator customKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder keyBuilder = new StringBuilder();
            keyBuilder.append(method.getName()).append(":");
            for (Object param : params) {
                if (param != null) {
                    keyBuilder.append(param.toString()).append(":");
                }
            }
            return keyBuilder.toString();
        };
    }
}
