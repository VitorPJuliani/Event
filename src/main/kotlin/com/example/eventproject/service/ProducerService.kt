package com.example.eventproject.service

import com.example.eventproject.exception.ResourceCreateException
import com.example.eventproject.exception.ResourceNotFoundException
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
        val producer = ProducerForm(data)

        return producerRepository.saveProducer(producer)!!
    }

    fun updateProducer(data: JsonNode, id: UUID): Producer? {
        val producer = ProducerForm(data)

        return producerRepository.updateProducer(producer, id)
    }

    fun deleteProducer(id: UUID): Int {
        return producerRepository.deleteProducer(id)
    }

    private fun validateProducerForm(producer: JsonNode) {
        if (!producer.has("name"))
            throw ResourceCreateException("Field name is missing")
    }

}
