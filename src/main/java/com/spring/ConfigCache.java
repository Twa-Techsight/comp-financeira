package com.spring;

import java.util.Collections;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import lombok.RequiredArgsConstructor;

@EnableCaching
@Configuration
@RequiredArgsConstructor
public class ConfigCache {

    
    private final ConfigCacheProperties cacheProperties;

    @Bean("springCM")
    public CacheManager cacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager("big-cache");

        Map<String, ConfigCacheProperties.CacheSpec> specs = cacheProperties.getSpecs();
        specs.keySet().forEach(cacheName -> {
            ConfigCacheProperties.CacheSpec spec = specs.get(cacheName);
            manager.registerCustomCache(cacheName, buildCache(spec));
        });

        manager.setCacheNames(Collections.emptyList());
        return manager;
    }

    private Cache<Object, Object> buildCache(ConfigCacheProperties.CacheSpec cacheSpec) {
        if (cacheSpec.getExpireAfter() == ConfigCacheProperties.ExpireAfter.ACCESS) {
            return Caffeine.newBuilder()
                    .expireAfterAccess(cacheSpec.getTimeout())
                    .build();
        }
        return Caffeine.newBuilder()
                .expireAfterWrite(cacheSpec.getTimeout())
                .build();
    }
    
}
