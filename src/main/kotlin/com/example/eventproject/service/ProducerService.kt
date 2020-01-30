package com.example.eventproject.service

import com.example.eventproject.model.Producer
import com.example.eventproject.repository.ProducerRepository
import org.springframework.stereotype.Service

class ProducerService(private val producerRepository: ProducerRepository) {

    fun findAllProducers(): List<Producer> {
        return producerRepository.findAllProducers()
    }
}
