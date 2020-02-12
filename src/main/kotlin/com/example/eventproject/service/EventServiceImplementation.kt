package com.example.eventproject.service

import com.example.eventproject.exception.ResourceCreateException
import com.example.eventproject.exception.ResourceNotFoundException
import com.example.eventproject.exception.ResourceUpdateException
import com.example.eventproject.form.EventForm
import com.example.eventproject.model.Event
import com.example.eventproject.repository.EventRepository
import java.util.UUID

class EventServiceImplementation(private val eventRepository: EventRepository): EventService {

    override fun findEventById(id: UUID): Event {
        return eventRepository.findEventById(id) ?: throw ResourceNotFoundException("Not found event with id: $id")
    }

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