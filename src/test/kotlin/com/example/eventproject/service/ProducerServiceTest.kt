package com.example.eventproject.service

import com.example.eventproject.exception.ResourceCreateException
import com.example.eventproject.exception.ResourceNotFoundException
import com.example.eventproject.exception.ResourceUpdateException
import com.example.eventproject.form.ProducerForm
import com.example.eventproject.model.Producer
import com.example.eventproject.repository.ProducerRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*

internal class ProducerServiceTest {

    private val producerForm = ProducerForm(
            name = "Vitor",
            email = "vitor@vitor.com",
            document = "123456"
    )

    private val producer = Producer(
            id = UUID.randomUUID(),
            name = "Vitor",
            email = "vitor@vitor.com",
            document = "123456"
    )

    private val repository = mockk<ProducerRepository>()

    private val service = ProducerService(repository)

    @Test
    fun `find producer by id should return producer object`() {
        val uuid = UUID.randomUUID()

        every {
            repository.findProducerById(uuid)
        } returns producer

        assertThat(service.findProducerById(uuid)).isEqualTo(producer)
    }

    @Test
    fun `find producer by id with nonexistent id should return ResourceNotFoundException`() {
        val uuid = UUID.randomUUID()

        every {
            repository.findProducerById(uuid)
        } returns null

        assertThatExceptionOfType(ResourceNotFoundException::class.java)
                .isThrownBy {
                    service.findProducerById(uuid)
                }
    }

    @Test
    fun `find all producers should return list of Producers`() {
        every {
            repository.findAllProducers()
        } returns listOf()

        assertThat(service.findAllProducers()).isNotNull
    }

    @Test
    fun `save producer with correct body should return producer object`() {
        every {
            repository.saveProducer(producerForm)
        } returns producer

        assertThat(service.saveProducer(producerForm)).isEqualTo(producer)
    }

    @Test
    fun `save producer with response null should return ResourceCreateException`() {
        every {
            repository.saveProducer(producerForm)
        } returns null

        assertThatExceptionOfType(ResourceCreateException::class.java)
                .isThrownBy {
                    service.saveProducer(producerForm)
                }
    }

    @Test
    fun `update producer with correct body should return producer object`() {
        val uuid = UUID.randomUUID()

        every {
            repository.updateProducer(producerForm, uuid)
        } returns producer

        assertThat(service.updateProducer(producerForm, uuid)).isEqualTo(producer)
    }

    @Test
    fun `update producer with response null should return ResourceUpdateException`() {
        val uuid = UUID.randomUUID()

        every {
            repository.updateProducer(producerForm, uuid)
        } returns null

        assertThatExceptionOfType(ResourceUpdateException::class.java)
                .isThrownBy {
                    service.updateProducer(producerForm, uuid)
                }
    }

    @Test
    fun `delete producer with correct id should not return ResourceNotFoundExceptrion`() {
        val uuid = UUID.randomUUID()

        every {
            repository.deleteProducer(uuid)
        } returns 1

        assertDoesNotThrow {
            service.deleteProducer(uuid)
        }
    }

    @Test
    fun `delete producer with nonexistent id should return ResourceNotFoundException`() {
        val uuid = UUID.randomUUID()

        every {
            repository.deleteProducer(uuid)
        } returns 0

        assertThatExceptionOfType(ResourceNotFoundException::class.java)
                .isThrownBy {
                    service.deleteProducer(uuid)
                }
    }
}