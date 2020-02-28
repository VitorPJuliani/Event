package com.example.eventproject.repository

import com.example.eventproject.form.ProducerForm
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID

@SpringBootTest
class JdbcProducerRepositoryTest(@Autowired private val repository: JdbcProducerRepository) {

    @Test
    fun `save producer`() {

        val producerForm = ProducerForm(name = "name", email = "email@email.com", document = "123456")

        val producer = repository.saveProducer(producerForm)

        assertThat(producer?.name).isEqualTo("name")
    }

    @Test
    fun `update producer`() {

        val producerForm = ProducerForm(name = "name", email = "email@email.com", document = "123456")

        val savedProducer = repository.saveProducer(producerForm)

        val updateProducerForm = ProducerForm(name = "name 2", email = "email@email.com", document = "123456")

        val updatedProducer = savedProducer?.id?.let {
            repository.updateProducer(updateProducerForm, it)
        }

        assertThat(updatedProducer?.name).isEqualTo("name 2")
    }

    @Test
    fun `update producer when id is nonexistent`() {

        val producerForm = ProducerForm(name = "name", email = "email@email.com", document = "123456")

        val producer = repository.updateProducer(producerForm, UUID.randomUUID())

        assertThat(producer).isNull()
    }

    @Test
    fun `delete producer`() {

        val producerForm = ProducerForm(name = "name", email = "email@email.com", document = "123456")

        val savedProducer = repository.saveProducer(producerForm)

        val status = savedProducer?.id?.let {
            repository.deleteProducer(it)
        }

        assertThat(status).isEqualTo(1)
    }

    @Test
    fun `delete producer when id is nonexistent`() {

        val status = repository.deleteProducer(UUID.randomUUID())

        assertThat(status).isEqualTo(0)
    }

    @Test
    fun `find producer by id`() {

        val producerForm = ProducerForm(name = "name", email = "email@email.com", document = "123456")

        val savedProducer = repository.saveProducer(producerForm)

        val producer = savedProducer?.id?.let {
            repository.findProducerById(it)
        }

        assertThat(producer?.name).isEqualTo("name")
    }

    @Test
    fun `find producer when id is nonexistent`() {

        val producer = repository.findProducerById(UUID.randomUUID())

        assertThat(producer).isNull()
    }

    @Test
    fun `find all producers`() {

        val producersForm = listOf<ProducerForm>(
                ProducerForm(name = "name 1", email = "email1@email.com", document = "1234"),
                ProducerForm(name = "name 2", email = "email2@email.com", document = "12345"),
                ProducerForm(name = "name 3", email = "email3@email.com", document = "123456")
        )

        producersForm.forEach {
            repository.saveProducer(it)
        }

        val producers = repository.findAllProducers()

        assertThat(producers).matches { it.size >= 3 }
    }

}