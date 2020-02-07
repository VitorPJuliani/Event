package com.example.eventproject.model

import java.util.UUID

data class Producer(
        val id: UUID,
        val name: String,
        val email: String,
        val document: String
)