package com.example.eventproject.controller

import com.example.eventproject.form.ProducerForm
import com.example.eventproject.model.Producer
import com.example.eventproject.service.ProducerService
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*


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
    fun updateProducer(@RequestBody producer: ProducerForm, @PathVariable("id") id: UUID): ResponseEntity<Producer> {
        val updatedProducer = producerService.updateProducer(producer, id)

        return ResponseEntity.ok(updatedProducer!!)
    }

    @DeleteMapping("/{id}")
    fun deleteProducer(@PathVariable("id") id: UUID): ResponseEntity<Any> {
        producerService.deleteProducer(id)

        return ResponseEntity.noContent().build()
    }
}