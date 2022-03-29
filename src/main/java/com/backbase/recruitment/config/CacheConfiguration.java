package com.backbase.recruitment.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfiguration extends CachingConfigurerSupport {

    public static final String KEYS_BP_WINNER = "best-pic-winners";

    @Bean("moveMemcacheKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new MoveMemcacheKeyGenerator();
    }

}
