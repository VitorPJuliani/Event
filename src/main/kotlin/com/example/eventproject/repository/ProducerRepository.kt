package com.example.eventproject.repository

import com.example.eventproject.model.Producer
import java.util.*

interface ProducerRepository {

//    fun findProducer(id: UUID): Producer

    fun findAllProducers(): List<Producer>
//
//    fun saveProducer(producer: Producer): Producer
//
//    fun updateProducer(producer: Producer): Producer
//
//    fun destroyProducer(id: UUID): String
}