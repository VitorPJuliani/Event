package com.example.eventproject.service

import com.example.eventproject.exception.ResourceCreateException
import com.example.eventproject.exception.ResourceNotFoundException
import com.example.eventproject.exception.ResourceUpdateException
import com.example.eventproject.form.ProducerForm
import com.example.eventproject.model.Producer
import com.example.eventproject.repository.ProducerRepository
import java.util.UUID

class ProducerServiceImplementation(private val producerRepository: ProducerRepository): ProducerService {

    override fun findAllProducers(): List<Producer> {
        return producerRepository.findAllProducers()
    }

    override fun findProducerById(id: UUID): Producer {
        return producerRepository.findProducerById(id)
                ?: throw ResourceNotFoundException("Not found producer with id: $id")
    }

    override fun saveProducer(producer: ProducerForm): Producer {
        return producerRepository.saveProducer(producer) ?: throw ResourceCreateException("Creating error")
    }

    override fun updateProducer(producer: ProducerForm, id: UUID): Producer {
        return producerRepository.updateProducer(producer, id)
                ?: throw ResourceUpdateException("Updating error: $id")
    }

    override fun deleteProducer(id: UUID) {
        if (producerRepository.deleteProducer(id) == 0)
            throw ResourceNotFoundException("Not found producer with id: $id")
    }
}
