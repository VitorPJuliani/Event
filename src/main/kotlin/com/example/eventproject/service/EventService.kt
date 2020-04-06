package com.example.eventproject.service

import com.example.eventproject.form.EventForm
import com.example.eventproject.model.Event
import java.util.UUID

interface EventService {

    fun findEventById(id: UUID): Event

    fun findAllEvents(): List<Event>

    fun saveEvent(event: EventForm): Event

    fun updateEvent(event: EventForm, id: UUID): Event

    fun deleteEvent(id: UUID)
}