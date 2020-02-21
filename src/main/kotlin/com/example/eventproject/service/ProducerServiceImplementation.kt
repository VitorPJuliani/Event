package com.example.eventproject.service

import com.example.eventproject.configuration.CacheConfiguration
import com.example.eventproject.configuration.CacheConfiguration.Caches.Companion.producers
import com.example.eventproject.exception.ResourceCreateException
import com.example.eventproject.exception.ResourceNotFoundException
import com.example.eventproject.exception.ResourceUpdateException
import com.example.eventproject.form.ProducerForm
import com.example.eventproject.model.Producer
import com.example.eventproject.repository.ProducerRepository
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import java.util.UUID

@CacheConfig (
        cacheManager = CacheConfiguration.caffeineCacheManager,
        cacheNames = [producers]
)
open class ProducerServiceImplementation(private val producerRepository: ProducerRepository): ProducerService {

    @Cacheable
    override fun findAllProducers(): List<Producer> {
        return producerRepository.findAllProducers()
    }

    @Cacheable
    override fun findProducerById(id: UUID): Producer {
        return producerRepository.findProducerById(id)
                ?: throw ResourceNotFoundException("Not found producer with id: $id")
    }

    @CacheEvict(allEntries = true)
    override fun saveProducer(producer: ProducerForm): Producer {
        return producerRepository.saveProducer(producer) ?: throw ResourceCreateException("Creating error")
    }

    @CacheEvict(allEntries = true)
    override fun updateProducer(producer: ProducerForm, id: UUID): Producer {
        return producerRepository.updateProducer(producer, id)
                ?: throw ResourceUpdateException("Updating error: $id")
    }

    @CacheEvict(allEntries = true)
    override fun deleteProducer(id: UUID) {
        if (producerRepository.deleteProducer(id) == 0)
            throw ResourceNotFoundException("Not found producer with id: $id")
    }
}
