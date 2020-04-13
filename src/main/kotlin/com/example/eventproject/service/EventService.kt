package com.example.eventproject.service

import com.example.eventproject.form.EventForm
import com.example.eventproject.model.EventResponse
import java.util.UUID

interface EventService {

    fun findEventById(id: UUID): EventResponse

    fun findAllEvents(): List<EventResponse>

    fun saveEvent(event: EventForm): EventResponse

    fun updateEvent(event: EventForm, id: UUID): EventResponse

    fun deleteEvent(id: UUID)
}