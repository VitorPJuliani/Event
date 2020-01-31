package com.example.eventproject.controller

import com.example.eventproject.dto.ProducerDto
import com.example.eventproject.model.Producer
import com.example.eventproject.service.ProducerService
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
    fun findProducerById(@PathVariable("id") id: String): ResponseEntity<Producer> {

        val uuid = UUID.fromString(id)

        val result = producerService.findProducerById(uuid)

        return ResponseEntity.ok(result!!)
    }

    @PostMapping
    fun saveProducer(@RequestBody producer: ProducerDto): ResponseEntity<Producer> {

        val savedProducer = producerService.saveProducer(producer)

        val location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProducer!!.id)
                .toUri()

        return ResponseEntity.created(location).body(savedProducer)
    }

    @PutMapping("/{id}")
    fun updateProducer(@RequestBody producer: ProducerDto, @PathVariable("id") id: String): ResponseEntity<Producer> {

        val uuid = UUID.fromString(id)

        val updatedProducer = producerService.updateProducer(producer, uuid)

        return ResponseEntity.ok(updatedProducer!!)
    }

    @DeleteMapping("/{id}")
    fun destroyProducer(@PathVariable("id") id: String): ResponseEntity<Any> {

        val uuid = UUID.fromString(id)

        producerService.destroyProducer(uuid)

        return ResponseEntity.noContent().build()
    }
}