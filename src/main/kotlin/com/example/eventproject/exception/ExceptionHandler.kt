package com.example.eventproject.exception

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
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

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val exceptionResponse = ExceptionResponse(status, Instant.now(), (ex.cause as MissingKotlinParameterException).path[0].fieldName)

        return ResponseEntity.status(exceptionResponse.status).body(exceptionResponse)
    }
}