package com.example.eventproject.configuration

import com.example.eventproject.properties.AppProperties
import com.example.eventproject.service.OpenWeatherImplementation
import com.example.eventproject.service.WeatherService
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConsumingRestApplicationConfiguration {

    @Bean
    fun openWeatherService(restTemplateBuilder: RestTemplateBuilder, appProperties: AppProperties): WeatherService {
        val restTemplate = restTemplateBuilder.build()
        val url = "${appProperties.openweather.url}?q={city}&appid=${appProperties.openweather.apiKey}"

        return OpenWeatherImplementation(restTemplate, url)
    }
}