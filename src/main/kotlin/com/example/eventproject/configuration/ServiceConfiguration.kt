package com.example.eventproject.configuration

import com.example.eventproject.repository.EventRepository
import com.example.eventproject.repository.ProducerRepository
import com.example.eventproject.service.ProducerService
import com.example.eventproject.service.ProducerServiceImplementation
import com.example.eventproject.service.CacheProducerServiceImplementation
import com.example.eventproject.service.EventService
import com.example.eventproject.service.EventServiceImplementation
import com.example.eventproject.service.CacheEventServiceImplementation
import com.example.eventproject.service.OpenWeatherService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {

    @Bean
    fun producerService(producerRepository: ProducerRepository) : ProducerService {
        return CacheProducerServiceImplementation(ProducerServiceImplementation(producerRepository))
    }

    @Bean
    fun eventService(eventRepository: EventRepository, openWeatherService: OpenWeatherService): EventService {
        return CacheEventServiceImplementation(EventServiceImplementation(eventRepository, openWeatherService))
    }

}