package com.example.eventproject.service

import com.example.eventproject.model.Producer
import com.example.eventproject.repository.ProducerRepository
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet
import java.util.*

class ProducerService(private val producerRepository: ProducerRepository) {

    fun findAllProducers(): List<Producer> {
        return producerRepository.findAllProducers()
    }
}
