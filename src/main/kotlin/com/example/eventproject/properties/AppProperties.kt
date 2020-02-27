package com.example.eventproject.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("app")
data class AppProperties(
        val cache: Cache
) {
    data class Cache(
            val expireTime: Long,
            val maxSize: Long)
}