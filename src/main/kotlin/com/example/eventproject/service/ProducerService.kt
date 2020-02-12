package com.example.eventproject.service

import com.example.eventproject.form.ProducerForm
import com.example.eventproject.model.Producer
import java.util.UUID

interface ProducerService {

    fun findAllProducers(): List<Producer>

    fun findProducerById(id: UUID): Producer

    fun saveProducer(producer: ProducerForm): Producer

    fun updateProducer(producer: ProducerForm, id: UUID): Producer

    fun deleteProducer(id: UUID)
}