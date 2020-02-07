package com.example.eventproject.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.Instant
import java.time.format.DateTimeParseException

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFoundHandler(ex: ResourceNotFoundException): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(HttpStatus.NOT_FOUND, Instant.now(), ex.message!!)

        return ResponseEntity.status(exceptionResponse.status).body(exceptionResponse)
    }

    @ExceptionHandler(ResourceCreateException::class)
    fun resourceCreateExceptionHandler(ex: ResourceCreateException): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(HttpStatus.BAD_REQUEST, Instant.now(), ex.message!!)

        return ResponseEntity.status(exceptionResponse.status).body(exceptionResponse)
    }

    @ExceptionHandler(ResourceUpdateException::class)
    fun resourceUpdateExceptionHandler(ex: ResourceUpdateException): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(HttpStatus.BAD_REQUEST, Instant.now(), ex.message!!)

        return ResponseEntity.status(exceptionResponse.status).body(exceptionResponse)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentExceptionHandler(ex: IllegalArgumentException): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(HttpStatus.BAD_REQUEST, Instant.now(), ex.message!!)

        return ResponseEntity.status(exceptionResponse.status).body(exceptionResponse)
    }

    @ExceptionHandler(DateTimeParseException::class)
    fun dateTimeParseExceptionHandler(ex: DateTimeParseException): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(HttpStatus.BAD_REQUEST, Instant.now(), ex.message!!)

        return ResponseEntity.status(exceptionResponse.status).body(exceptionResponse)
    }
}