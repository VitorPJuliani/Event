package com.example.eventproject.configuration

import com.example.eventproject.repository.EventRepository
import com.example.eventproject.repository.ProducerRepository
import com.example.eventproject.service.*
import com.example.eventproject.temperature.converter.TemperatureConverter
import com.example.eventproject.temperature.formatter.TemperatureFormatter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {

    @Bean
    fun producerService(producerRepository: ProducerRepository) : ProducerService {
        return CacheProducerServiceImplementation(ProducerServiceImplementation(producerRepository))
    }

    @Bean
    fun eventService(eventRepository: EventRepository) : EventService {
        return EventServiceImplementation(eventRepository)
    }

    @Bean
    fun eventTemperatureService(eventService: EventService, weatherService: WeatherService, @Qualifier("KelvinToCelsius") temperatureConverter: TemperatureConverter, @Qualifier("DoubleDecimalTemperature") temperatureFormatter: TemperatureFormatter): EventTemperatureService {
        return CacheEventServiceImplementation(EventTemperatureImplementation(eventService, weatherService, temperatureConverter, temperatureFormatter))
    }

}