package com.example.eventproject.model

import java.time.LocalDate
import java.util.UUID

data class Event(
        val id: UUID,
        val name: String,
        val description: String,
        val date: LocalDate,
        val producer: UUID
)