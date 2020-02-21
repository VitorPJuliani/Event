package com.example.eventproject.configuration

import com.example.eventproject.configuration.CacheConfiguration.Caches.Companion.events
import com.example.eventproject.configuration.CacheConfiguration.Caches.Companion.producers
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit


@Configuration
@EnableCaching
class CacheConfiguration {

    companion object {
        const val caffeineCacheManager = "caffeineCacheManager"
    }

    interface Caches {
        companion object {
            const val producers = "producers"
            const val events = "events"
        }
    }

    @Bean(caffeineCacheManager)
    fun cacheManager(): CacheManager {
        val caffeineCacheManager = CaffeineCacheManager(producers, events)

        caffeineCacheManager.setCaffeine(caffeine())

        return caffeineCacheManager
    }

    private fun caffeine(): Caffeine<Any, Any> {
        return Caffeine.newBuilder()
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .maximumSize(500)
    }

}