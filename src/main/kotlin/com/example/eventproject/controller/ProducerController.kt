package com.example.eventproject.controller

import com.example.eventproject.model.Producer
import com.example.eventproject.service.ProducerService
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.UUID


@RestController
@RequestMapping("/producers")
class ProducerController(private val producerService: ProducerService) {

    @GetMapping
    fun findAllProducers(): ResponseEntity<List<Producer>> {
        val result = producerService.findAllProducers()

        return ResponseEntity.ok(result)
    }

    @GetMapping("/{id}")
    fun findProducerById(@PathVariable("id") id: UUID): ResponseEntity<Producer> {
        val result = producerService.findProducerById(id)

        return ResponseEntity.ok(result)
    }

    @PostMapping
    fun saveProducer(@RequestBody producer: JsonNode): ResponseEntity<Producer> {
        val savedProducer = producerService.saveProducer(producer)

        val location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/${savedProducer.id}")
                .build()
                .toUri()

        return ResponseEntity.created(location).body(savedProducer)
    }

    @PutMapping("/{id}")
    fun updateProducer(@RequestBody producer: JsonNode, @PathVariable("id") id: UUID): ResponseEntity<Producer> {
        val updatedProducer = producerService.updateProducer(producer, id)

        return ResponseEntity.ok(updatedProducer!!)
    }

    @DeleteMapping("/{id}")
    fun deleteProducer(@PathVariable("id") id: UUID): ResponseEntity<Any> {
        producerService.deleteProducer(id)

        return ResponseEntity.noContent().build()
    }
}