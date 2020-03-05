package com.example.eventproject.configuration

import com.example.eventproject.converter.WeatherConverter
import com.example.eventproject.converter.WeatherConverterInterface
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConverterConfiguration {

    @Bean
    fun weatherConverter(): WeatherConverterInterface {
        return WeatherConverter()
    }
}