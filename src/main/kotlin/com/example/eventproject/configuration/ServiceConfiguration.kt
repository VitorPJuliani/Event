package com.example.eventproject.configuration

import com.example.eventproject.repository.EventRepository
import com.example.eventproject.repository.ProducerRepository
import com.example.eventproject.service.EventService
import com.example.eventproject.service.ProducerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {

    @Bean
    fun producerService(producerRepository: ProducerRepository) : ProducerService {
        return ProducerService(producerRepository)
    }

    @Bean
    fun eventService(eventRepository: EventRepository): EventService {
        return EventService(eventRepository)
    }

}