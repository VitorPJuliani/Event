package com.example.eventproject.service

import com.example.eventproject.configuration.CacheConfiguration.Caches.Companion.events
import com.example.eventproject.configuration.CacheConfiguration.Companion.caffeineCacheManager
import com.example.eventproject.form.EventForm
import com.example.eventproject.model.Event
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import java.util.UUID

@CacheConfig(
        cacheManager = caffeineCacheManager,
        cacheNames = [events]
)
open class CacheEventServiceImplementation(private val eventServiceImplementation: EventService): EventService {

    @Cacheable
    override fun findEventById(id: UUID): Event {
        return eventServiceImplementation.findEventById(id)
    }

    @Cacheable
    override fun findAllEvents(): List<Event> {
        return eventServiceImplementation.findAllEvents()
    }

    @CacheEvict(allEntries = true)
    override fun saveEvent(event: EventForm): Event {
        return eventServiceImplementation.saveEvent(event)
    }

    @CacheEvict(allEntries = true)
    override fun updateEvent(event: EventForm, id: UUID): Event {
        return eventServiceImplementation.updateEvent(event, id)
    }

    @CacheEvict(allEntries = true)
    override fun deleteEvent(id: UUID) {
        return eventServiceImplementation.deleteEvent(id)
    }
}