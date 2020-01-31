package com.example.eventproject.exception

import org.springframework.http.HttpStatus
import java.time.Instant

data class ExceptionResponse(
        val status: HttpStatus,
        val timestamp: Instant,
        val message: String
)