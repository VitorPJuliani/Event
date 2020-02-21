package com.example.eventproject.configuration

import com.example.eventproject.cache.KeyGenerator
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeyConfiguration: CachingConfigurerSupport() {

    @Bean
    override fun keyGenerator(): KeyGenerator {
        return KeyGenerator()
    }
}