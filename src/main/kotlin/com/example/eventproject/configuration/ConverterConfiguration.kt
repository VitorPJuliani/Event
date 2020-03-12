package com.example.eventproject.configuration

import com.example.eventproject.temperature.converter.KelvinToCelsius
import com.example.eventproject.temperature.converter.TemperatureConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConverterConfiguration {

    @Bean(name = ["KelvinToCelsius"])
    fun kelvinToCelsius(): TemperatureConverter {
        return KelvinToCelsius()
    }
}