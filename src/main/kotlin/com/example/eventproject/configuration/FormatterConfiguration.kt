package com.example.eventproject.configuration

import com.example.eventproject.temperature.formatter.TemperatureFormatter
import com.example.eventproject.temperature.formatter.TemperatureFormatters
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FormatterConfiguration {

    @Bean(name = ["celsiusFormat"])
    fun temperatureFormatter(): TemperatureFormatter {
        return TemperatureFormatters.CELSIUS_FORMAT
    }
}