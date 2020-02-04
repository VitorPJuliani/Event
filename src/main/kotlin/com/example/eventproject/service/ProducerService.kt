package com.example.eventproject.service

import com.example.eventproject.exception.ResourceNotFoundException
import com.example.eventproject.exception.ResourceUpdateException
import com.example.eventproject.form.ProducerForm
import com.example.eventproject.model.Producer
import com.example.eventproject.repository.ProducerRepository
import com.fasterxml.jackson.databind.JsonNode
import java.util.UUID

class ProducerService(private val producerRepository: ProducerRepository) {

    fun findAllProducers(): List<Producer> {
        return producerRepository.findAllProducers()
    }

    fun findProducerById(id: UUID): Producer {
        try {
            return producerRepository.findProducerById(id)!!
        } catch (e: Exception) {
            throw ResourceNotFoundException("Not found producer with id: $id")
        }
    }

    fun saveProducer(data: JsonNode): Producer {
        return producerRepository.saveProducer(ProducerForm(data))!!
    }

    fun updateProducer(data: JsonNode, id: UUID): Producer? {
        val producer = ProducerForm(data)

        return producerRepository.updateProducer(producer, id) ?: throw ResourceUpdateException("Invalid id: $id")
    }

    fun deleteProducer(id: UUID): Int {
        val statusDelete = producerRepository.deleteProducer(id)

        if (statusDelete == 0)
            throw ResourceUpdateException("User not found to delete with id: $id")

        return statusDelete
    }
}
