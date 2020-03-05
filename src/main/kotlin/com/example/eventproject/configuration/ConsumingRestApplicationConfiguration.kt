package com.example.eventproject.configuration

import com.example.eventproject.properties.AppProperties
import com.example.eventproject.service.WeatherService
import com.example.eventproject.service.OpenWeatherImplementation
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
    fun openWeatherService(restTemplate: RestTemplate, appProperties: AppProperties): WeatherService {
        return OpenWeatherImplementation(restTemplate, appProperties.openweather)
    }
}