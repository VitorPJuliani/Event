package com.example.eventproject.service

import com.example.eventproject.configuration.CacheConfiguration.Caches.Companion.events
import com.example.eventproject.configuration.CacheConfiguration.Companion.caffeineCacheManager
import com.example.eventproject.exception.ResourceCreateException
import com.example.eventproject.exception.ResourceNotFoundException
import com.example.eventproject.exception.ResourceUpdateException
import com.example.eventproject.form.EventForm
import com.example.eventproject.model.Event
import com.example.eventproject.repository.EventRepository
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import java.util.UUID

@CacheConfig(
        cacheManager = caffeineCacheManager,
        cacheNames = [events]
)
open class EventServiceImplementation(private val eventRepository: EventRepository): EventService {

    @Cacheable
    override fun findEventById(id: UUID): Event {
        return eventRepository.findEventById(id) ?: throw ResourceNotFoundException("Not found event with id: $id")
    }

    @Cacheable
    override fun findAllEvents(): List<Event> {
        return eventRepository.findAllEvents()
    }

    override fun saveEvent(event: EventForm): Event {
        return eventRepository.saveEvent(event) ?: throw ResourceCreateException("Creating error")
    }

    override fun updateEvent(event: EventForm, id: UUID): Event {
        return eventRepository.updateEvent(event, id) ?: throw ResourceUpdateException("Updating error")
    }

    override fun deleteEvent(id: UUID) {
        if (eventRepository.deleteEvent(id) == 0)
            throw ResourceNotFoundException("Not found event with id: $id")
    }
}