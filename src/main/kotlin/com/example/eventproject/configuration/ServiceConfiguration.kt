package com.example.eventproject.configuration

import com.example.eventproject.repository.EventRepository
import com.example.eventproject.repository.ProducerRepository
import com.example.eventproject.service.CacheEventServiceImplementation
import com.example.eventproject.service.ProducerService
import com.example.eventproject.service.ProducerServiceImplementation
import com.example.eventproject.service.CacheProducerServiceImplementation
import com.example.eventproject.service.EventService
import com.example.eventproject.service.EventServiceImplementation
import com.example.eventproject.service.WeatherService
import com.example.eventproject.service.EventTemperatureImplementation
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
    fun eventService(eventRepository: EventRepository, weatherService: WeatherService, @Qualifier("kelvinToCelsius") temperatureConverter: TemperatureConverter, @Qualifier("celsiusFormat") temperatureFormatter: TemperatureFormatter) : EventService {
        return EventTemperatureImplementation(CacheEventServiceImplementation(EventServiceImplementation(eventRepository)), weatherService, temperatureConverter, temperatureFormatter)
    }
}