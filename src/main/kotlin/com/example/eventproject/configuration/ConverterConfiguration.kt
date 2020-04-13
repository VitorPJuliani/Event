package com.example.eventproject.configuration

import com.example.eventproject.temperature.converter.TemperatureConverter
import com.example.eventproject.temperature.converter.TemperatureConverters
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConverterConfiguration {

    @Bean(name = ["kelvinToCelsius"])
    fun kelvinToCelsius(): TemperatureConverter {
        return TemperatureConverters.KELVIN_TO_CELSIUS
    }
}