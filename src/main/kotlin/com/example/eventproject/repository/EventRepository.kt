package com.example.eventproject.repository

import com.example.eventproject.model.Event
import java.util.*

interface EventRepository {

    fun findEvent(id: UUID): Event

    fun findAllEvents(): List<Event>

    fun saveEvent(event: Event): Event

    fun updateEvent(Event: Event): Event

    fun destroyEvent(id: UUID): Int
}