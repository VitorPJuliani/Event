package com.example.eventproject.repository

import com.example.eventproject.form.EventForm
import com.example.eventproject.form.ProducerForm
import org.assertj.core.api.Assertions.assertThat
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.jdbc.core.JdbcTemplate
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils
import java.time.LocalDate
import java.util.UUID

@Testcontainers
class JdbcEventRepositoyTest {

    companion object {
        @Container
        private var postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:9.6-alpine").apply {
            withDatabaseName("eventprojecttest")
            start()
        }
    }

    private lateinit var eventRepository: JdbcEventRepository
    private lateinit var producerRepository: JdbcProducerRepository

    @BeforeEach
    fun start() {

        val databaseName = RandomStringUtils.randomAlphabetic(8).toLowerCase()

        postgreSQLContainer.execInContainer("psql", "postgresql://${postgreSQLContainer.username}:${postgreSQLContainer.password}@localhost:5432/eventprojecttest", "-c", "CREATE DATABASE $databaseName;")
        postgreSQLContainer.withDatabaseName(databaseName)

        val dataSource = DataSourceBuilder.create()
                .url(postgreSQLContainer.jdbcUrl)
                .username(postgreSQLContainer.username)
                .password(postgreSQLContainer.password)
                .driverClassName(postgreSQLContainer.driverClassName)
                .build()

        Flyway.configure().dataSource(dataSource).load().migrate()

        val jdbcTemplate = JdbcTemplate(dataSource)

        eventRepository = JdbcEventRepository(jdbcTemplate)
        producerRepository = JdbcProducerRepository(jdbcTemplate)
    }

    @Test
    fun saveEvent() {

        val producerForm = ProducerForm(name = "name", email = "email@email.com", document = "123456")

        val producer = producerRepository.saveProducer(producerForm)

        val eventForm = producer?.id?.let {
            EventForm(
                    name = "name",
                    description = "description",
                    date = LocalDate.parse("2020-02-18"),
                    producer = it
            )
        }

        val event = eventForm?.let {
            eventRepository.saveEvent(it)
        }

        assertThat(event?.name).isEqualTo("name")
    }

    @Test
    fun saveEventWhenIncorrectBody() {

        val eventForm = EventForm(name = "name", description = "description", date = LocalDate.parse("2020-02-19"), producer = UUID.randomUUID())

        val event = eventRepository.saveEvent(eventForm)

        assertThat(event).isNull()
    }

    @Test
    fun updateEvent() {

        val producerForm = ProducerForm(name = "name", email = "email@email.com", document = "123456")

        val producer = producerRepository.saveProducer(producerForm)

        val eventForm = producer?.id?.let {
            EventForm(
                    name = "name",
                    description = "description",
                    date = LocalDate.parse("2020-02-18"),
                    producer = it
            )
        }

        val event = eventForm?.let {
            eventRepository.saveEvent(it)
        }

        val queryEventForm = producer?.id?.let {
            EventForm(
                    name = "updated name",
                    description = "description",
                    date = LocalDate.parse("2020-02-18"),
                    producer = it
            )
        }

        val updatedEvent = queryEventForm?.let { form ->
            event?.id?.let { id ->
                eventRepository.updateEvent(form, id)
            }
        }

        assertThat(updatedEvent?.name).isEqualTo("updated name")
    }

    @Test
    fun updateEventWhenIdIsNonexistent() {

        val producerForm = ProducerForm(name = "name", email = "email@email.com", document = "123456")

        val producer = producerRepository.saveProducer(producerForm)

        val eventForm = producer?.id?.let {
            EventForm(name = "name", description = "description", date = LocalDate.parse("2020-02-18"), producer = it)
        }?.also {
            eventRepository.saveEvent(it)
        }

        val event = eventForm?.let {
            eventRepository.updateEvent(it, UUID.randomUUID())
        }

        assertThat(event).isNull()
    }

    @Test
    fun updateEventWhenIncorrectBody() {

        val producerForm = ProducerForm(name = "name", email = "email@email.com", document = "123456")

        val savedProducer = producerRepository.saveProducer(producerForm)

        val eventForm = savedProducer?.id?.let {
            EventForm(name = "name", description = "description", date = LocalDate.parse("2020-02-18"), producer = it)
        }

        val savedEvent = eventForm?.let {
            eventRepository.saveEvent(eventForm)
        }

        val queryEventForm = EventForm(name = "name", description = "description", date = LocalDate.parse("2020-02-18"), producer = UUID.randomUUID())

        val event = savedEvent?.id?.let { id ->
            eventRepository.updateEvent(queryEventForm, id)
        }

        assertThat(event).isNull()
    }

    @Test
    fun deleteEvent() {

        val producerForm = ProducerForm(name = "name", email = "email@email.com", document = "123456")

        val producer = producerRepository.saveProducer(producerForm)

        val eventForm = producer?.id?.let {
            EventForm(
                    name = "name",
                    description = "description",
                    date = LocalDate.parse("2020-02-18"),
                    producer = it
            )
        }

        val event = eventForm?.let {
            eventRepository.saveEvent(it)
        }

        val status = event?.id?.let {
            eventRepository.deleteEvent(it)
        }

        assertThat(status).isEqualTo(1)
    }

    @Test
    fun deleteEventWhenIdIsNonexistent() {

        val status = eventRepository.deleteEvent(UUID.randomUUID())

        assertThat(status).isEqualTo(0)
    }

    @Test
    fun findEventById() {

        val producerForm = ProducerForm(name = "name", email = "email@email.com", document = "123456")

        val producer = producerRepository.saveProducer(producerForm)

        val eventForm = producer?.id?.let {
            EventForm(
                    name = "name",
                    description = "description",
                    date = LocalDate.parse("2020-02-18"),
                    producer = it
            )
        }

        val event = eventForm?.let {
            eventRepository.saveEvent(it)
        }

        val eventResult = event?.id?.let {
            eventRepository.findEventById(it)
        }

        assertThat(eventResult).isNotNull
        assertThat(eventResult?.name).isEqualTo("name")
    }

    @Test
    fun findEventByIdWhenIdIsNonexistent() {

        val eventResult = eventRepository.findEventById(UUID.randomUUID())

        assertThat(eventResult).isNull()
    }

    @Test
    fun findAllEvents() {

        val producerForm = ProducerForm(name = "name", email = "email@email.com", document = "123456")

        val producer = producerRepository.saveProducer(producerForm)

        val events = producer?.id?.let {
            listOf(
                    EventForm(name = "name", description = "description", date = LocalDate.parse("2020-02-18"), producer = it),
                    EventForm(name = "name 2", description = "description", date = LocalDate.parse("2020-02-18"), producer = it),
                    EventForm(name = "name 3", description = "description", date = LocalDate.parse("2020-02-18"), producer = it)
            )
        }

        events?.forEach {
            eventRepository.saveEvent(it)
        }

        val eventsResult = eventRepository.findAllEvents()

        assertThat(eventsResult).hasSize(3)
    }
}
