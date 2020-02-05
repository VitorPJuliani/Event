package com.example.eventproject.service

import com.example.eventproject.exception.ResourceCreateException
import com.example.eventproject.exception.ResourceNotFoundException
import com.example.eventproject.exception.ResourceUpdateException
import com.example.eventproject.form.ProducerForm
import com.example.eventproject.model.Producer
import com.example.eventproject.repository.ProducerRepository
import java.util.UUID

class ProducerService(private val producerRepository: ProducerRepository) {

    fun findAllProducers(): List<Producer> {
        return producerRepository.findAllProducers()
    }

    fun findProducerById(id: UUID): Producer {
        return producerRepository.findProducerById(id)
                ?: throw ResourceNotFoundException("Not found producer with id: $id")
    }

    fun saveProducer(producer: ProducerForm): Producer {
        return producerRepository.saveProducer(producer) ?: throw ResourceCreateException("Creating error")
    }

    fun updateProducer(producer: ProducerForm, id: UUID): Producer {
        return producerRepository.updateProducer(producer, id)
                ?: throw ResourceUpdateException("Updating error: $id")
    }

    fun deleteProducer(id: UUID) {
        if (producerRepository.deleteProducer(id) == 0)
            throw ResourceNotFoundException("Not found producer with id: $id")
    }
}
