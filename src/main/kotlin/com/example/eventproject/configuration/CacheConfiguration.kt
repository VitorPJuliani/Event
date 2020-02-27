package com.example.eventproject.configuration

import com.example.eventproject.configuration.CacheConfiguration.Caches.Companion.events
import com.example.eventproject.configuration.CacheConfiguration.Caches.Companion.producers
import com.example.eventproject.properties.AppProperties
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
    fun cacheManager(appProperties: AppProperties): CacheManager {
        val caffeineCacheManager = CaffeineCacheManager(producers, events)

        caffeineCacheManager.setCaffeine(caffeine(appProperties.cache))

        return caffeineCacheManager
    }

    private fun caffeine(appPropertiesCache: AppProperties.Cache): Caffeine<Any, Any> {
        return Caffeine.newBuilder()
                .expireAfterAccess(appPropertiesCache.expireTime, TimeUnit.MINUTES)
                .maximumSize(appPropertiesCache.maxSize)
    }

}