package com.example.eventproject.controller

import com.example.eventproject.model.Producer
import com.example.eventproject.service.ProducerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/producers")
class ProducerController(private val producerService: ProducerService) {

    @GetMapping
    fun findAllProducers(): ResponseEntity<List<Producer>> {
        val result = producerService.findAllProducers()

        return ResponseEntity.ok(result)
    }


}