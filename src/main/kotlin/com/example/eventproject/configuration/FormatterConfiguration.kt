package com.example.eventproject.configuration

import com.example.eventproject.temperature.formatter.DoubleDecimalTemperature
import com.example.eventproject.temperature.formatter.TemperatureFormatter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FormatterConfiguration {

    @Bean(name = ["DoubleDecimalTemperature"])
    fun temperatureFormatter(): TemperatureFormatter {
        return DoubleDecimalTemperature()
    }
}