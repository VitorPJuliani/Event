package com.example.eventproject.repository

import com.example.eventproject.dto.ProducerDto
import com.example.eventproject.model.Producer
import java.util.*

interface ProducerRepository {

    fun findProducer(id: UUID): Producer?

    fun findAllProducers(): List<Producer>

    fun saveProducer(producer: ProducerDto): Producer?

    fun updateProducer(producer: ProducerDto, id: UUID): Producer?

    fun destroyProducer(id: UUID): Int
}