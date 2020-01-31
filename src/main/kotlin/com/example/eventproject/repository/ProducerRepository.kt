package com.example.eventproject.repository

import com.example.eventproject.form.ProducerForm
import com.example.eventproject.model.Producer
import java.util.*

interface ProducerRepository {

    fun findProducerById(id: UUID): Producer?

    fun findAllProducers(): List<Producer>

    fun saveProducer(producer: ProducerForm): Producer?

    fun updateProducer(producer: ProducerForm, id: UUID): Producer?

    fun deleteProducer(id: UUID): Int
}