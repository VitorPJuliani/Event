package com.example.eventproject.service

import com.example.eventproject.configuration.CacheConfiguration.Caches.Companion.producers
import com.example.eventproject.configuration.CacheConfiguration.Companion.caffeineCacheManager
import com.example.eventproject.form.ProducerForm
import com.example.eventproject.model.Producer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import java.util.UUID

@CacheConfig(
        cacheManager = caffeineCacheManager,
        cacheNames = [producers]
)
open class CacheProducerServiceImplementation(@Autowired private val producerServiceImplementation: ProducerServiceImplementation): ProducerService {

    @Cacheable
    override fun findAllProducers(): List<Producer> {
        return producerServiceImplementation.findAllProducers()
    }

    @Cacheable
    override fun findProducerById(id: UUID): Producer {
        return producerServiceImplementation.findProducerById(id)
    }

    @CacheEvict(allEntries = true)
    override fun saveProducer(producer: ProducerForm): Producer {
        return producerServiceImplementation.saveProducer(producer)
    }

    @CacheEvict(allEntries = true)
    override fun updateProducer(producer: ProducerForm, id: UUID): Producer {
        return producerServiceImplementation.updateProducer(producer, id)
    }

    @CacheEvict(allEntries = true)
    override fun deleteProducer(id: UUID) {
        producerServiceImplementation.deleteProducer(id)
    }
}