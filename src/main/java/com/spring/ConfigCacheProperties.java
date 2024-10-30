package com.spring;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "spring.cache")
public class ConfigCacheProperties {

    private static final int DEFAULT_CACHE_SIZE = 100;

    private Map<String, CacheSpec> specs = new HashMap<>();

    enum ExpireAfter {
	WRITE, ACCESS
    }

    @Data
    public static class CacheSpec {
	private Duration timeout;
	private Integer maxSize = DEFAULT_CACHE_SIZE;
	private ExpireAfter expireAfter = ExpireAfter.WRITE;
    }

}
