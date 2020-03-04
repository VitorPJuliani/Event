package com.example.eventproject.configuration

import com.example.eventproject.properties.AppProperties
import com.example.eventproject.service.OpenWeatherService
import com.example.eventproject.service.OpenWeatherServiceImplementation
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class ConsumingRestApplicationConfiguration {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build()
    }

    @Bean
    fun openWeatherService(restTemplate: RestTemplate, appProperties: AppProperties): OpenWeatherService {
        return OpenWeatherServiceImplementation(restTemplate, appProperties.openweather.apiKey)
    }
}