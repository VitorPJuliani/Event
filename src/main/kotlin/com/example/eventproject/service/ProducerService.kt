package com.example.eventproject.service

import com.example.eventproject.dto.ProducerDto
import com.example.eventproject.model.Producer
import com.example.eventproject.repository.ProducerRepository
import org.springframework.stereotype.Service
import java.util.*

class ProducerService(private val producerRepository: ProducerRepository) {

    fun findAllProducers(): List<Producer> {
        return producerRepository.findAllProducers()
    }

    fun findProducerById(id: UUID): Producer? {
        return producerRepository.findProducer(id)
    }

    fun saveProducer(producer: ProducerDto): Producer? {
        return producerRepository.saveProducer(producer)
    }

    fun updateProducer(producer: ProducerDto, id: UUID): Producer? {
        return producerRepository.updateProducer(producer, id)
    }

}
