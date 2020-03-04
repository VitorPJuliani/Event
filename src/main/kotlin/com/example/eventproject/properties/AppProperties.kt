package com.example.eventproject.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("app")
data class AppProperties(
        val cache: Cache,
        val openweather: OpenWeather
) {
    data class Cache(
            val expireTimeInMinutes: Long,
            val maxSize: Long)

    data class OpenWeather(
            val apiKey: String
    )
}