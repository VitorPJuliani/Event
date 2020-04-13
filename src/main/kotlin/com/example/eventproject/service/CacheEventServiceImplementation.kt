package com.example.eventproject.service

import com.example.eventproject.configuration.CacheConfiguration.Caches.Companion.events
import com.example.eventproject.configuration.CacheConfiguration.Companion.caffeineCacheManager
import com.example.eventproject.form.EventForm
import com.example.eventproject.model.EventResponse
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import java.util.UUID

@CacheConfig(
        cacheManager = caffeineCacheManager,
        cacheNames = [events]
)
open class CacheEventServiceImplementation(private val eventService: EventService): EventService {

    @Cacheable
    override fun findEventById(id: UUID): EventResponse {
        return eventService.findEventById(id)
    }

    @Cacheable
    override fun findAllEvents(): List<EventResponse> {
        return eventService.findAllEvents()
    }

    @CacheEvict(allEntries = true)
    override fun saveEvent(event: EventForm): EventResponse {
        return eventService.saveEvent(event)
    }

    @CacheEvict(allEntries = true)
    override fun updateEvent(event: EventForm, id: UUID): EventResponse {
        return eventService.updateEvent(event, id)
    }

    @CacheEvict(allEntries = true)
    override fun deleteEvent(id: UUID) {
        return eventService.deleteEvent(id)
    }
}