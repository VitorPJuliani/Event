package com.example.eventproject.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.Instant

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
}