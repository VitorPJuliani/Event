package com.example.eventproject.model

import java.time.LocalDate
import java.util.UUID

data class EventResponse(
        val id: UUID,
        val name: String,
        val description: String,
        val date: LocalDate,
        val city: String,
        val producer: UUID,
        val weather: String
)