package com.example.eventproject.repository

import com.example.eventproject.annotation.PostgresDatasource
import com.example.eventproject.form.EventForm
import com.example.eventproject.form.ProducerForm
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import java.util.UUID

@PostgresDatasource
class JdbcEventRepositoyTest {

    @Autowired
    private lateinit var basePostgresContainer: BasePostgresContainer

    private lateinit var eventRepository: JdbcEventRepository
    private lateinit var producerRepository: JdbcProducerRepository

    @BeforeEach
    fun setup() {

        val jdbcTemplate = basePostgresContainer.jdbcTemplate()

        eventRepository = JdbcEventRepository(jdbcTemplate)
        producerRepository = JdbcProducerRepository(jdbcTemplate)
    }

    @AfterEach
    fun tearDown() {
        basePostgresContainer.clearPool()
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

        Assertions.assertThat(event?.name).isEqualTo("name")
    }

    @Test
    fun saveEventWhenIncorrectBody() {

        val eventForm = EventForm(name = "name", description = "description", date = LocalDate.parse("2020-02-19"), producer = UUID.randomUUID())

        val event = eventRepository.saveEvent(eventForm)

        Assertions.assertThat(event).isNull()
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

        Assertions.assertThat(updatedEvent?.name).isEqualTo("updated name")
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

        Assertions.assertThat(event).isNull()
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

        Assertions.assertThat(event).isNull()
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

        Assertions.assertThat(status).isEqualTo(1)
    }

    @Test
    fun deleteEventWhenIdIsNonexistent() {

        val status = eventRepository.deleteEvent(UUID.randomUUID())

        Assertions.assertThat(status).isEqualTo(0)
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

        Assertions.assertThat(eventResult).isNotNull
        Assertions.assertThat(eventResult?.name).isEqualTo("name")
    }

    @Test
    fun findEventByIdWhenIdIsNonexistent() {

        val eventResult = eventRepository.findEventById(UUID.randomUUID())

        Assertions.assertThat(eventResult).isNull()
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

        Assertions.assertThat(eventsResult).hasSize(3)
    }
}
