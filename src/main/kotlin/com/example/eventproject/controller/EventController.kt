package com.example.eventproject.controller

import com.example.eventproject.form.EventForm
import com.example.eventproject.model.EventResponse
import com.example.eventproject.service.EventService
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.UUID

@RestController
@RequestMapping("/events")
class EventController(private val eventService: EventService) {

    @GetMapping("/{id}")
    fun findEventById(@PathVariable("id") id: UUID): ResponseEntity<EventResponse> {
        val event = eventService.findEventById(id)

        return ResponseEntity.ok(event)
    }

    @GetMapping
    fun findAllEvents(): ResponseEntity<List<EventResponse>> {
        return ResponseEntity.ok(eventService.findAllEvents())
    }

    @PostMapping
    fun saveEvent(@RequestBody data: JsonNode): ResponseEntity<EventResponse> {
        val event = EventForm(data)

        val savedEvent = eventService.saveEvent(event)

        val location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/${savedEvent.id}")
                .build()
                .toUri()

        return ResponseEntity.created(location).body(savedEvent)
    }

    @PutMapping("/{id}")
    fun updateEvent(@RequestBody data: JsonNode, @PathVariable("id") id: UUID): ResponseEntity<EventResponse> {
        val event = EventForm(data)

        val updatedEvent = eventService.updateEvent(event, id)

        return ResponseEntity.ok(updatedEvent)
    }

    @DeleteMapping("/{id}")
    fun deleteEvent(@PathVariable("id") id: UUID): ResponseEntity<Any> {

        eventService.deleteEvent(id)

        return ResponseEntity.noContent().build()
    }
}