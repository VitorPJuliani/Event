package com.example.eventproject.service

import com.example.eventproject.exception.ResourceCreateException
import com.example.eventproject.exception.ResourceNotFoundException
import com.example.eventproject.exception.ResourceUpdateException
import com.example.eventproject.form.EventForm
import com.example.eventproject.model.EventResponse
import com.example.eventproject.repository.EventRepository
import java.util.UUID

class EventServiceImplementation(private val eventRepository: EventRepository) : EventService {

    override fun findEventById(id: UUID): EventResponse {
        val event = eventRepository.findEventById(id) ?: throw ResourceNotFoundException("Not found event with id: $id")

        return EventResponse(event)
    }

    override fun findAllEvents(): List<EventResponse> {
        val events = eventRepository.findAllEvents()

        return events.map {
            EventResponse(it)
        }
    }

    override fun saveEvent(event: EventForm): EventResponse {
        val savedEvent = eventRepository.saveEvent(event) ?: throw ResourceCreateException("Creating error")

        return EventResponse(savedEvent)
    }

    override fun updateEvent(event: EventForm, id: UUID): EventResponse {
        val updatedEvent = eventRepository.updateEvent(event, id) ?: throw ResourceUpdateException("Updating error")

        return EventResponse(updatedEvent)
    }

    override fun deleteEvent(id: UUID) {
        if (eventRepository.deleteEvent(id) == 0)
            throw ResourceNotFoundException("Not found event with id: $id")
    }
}