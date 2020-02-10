package com.example.eventproject.service

import com.example.eventproject.exception.ResourceCreateException
import com.example.eventproject.exception.ResourceNotFoundException
import com.example.eventproject.exception.ResourceUpdateException
import com.example.eventproject.form.EventForm
import com.example.eventproject.model.Event
import com.example.eventproject.repository.EventRepository
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.time.LocalDate
import java.util.UUID

internal class EventServiceTest {

    private val event = Event(
            id = UUID.randomUUID(),
            name = "Name",
            description = "Description",
            date = LocalDate.parse("2020-02-10"),
            producer = UUID.randomUUID()
    )

    private val eventForm = EventForm(
            name = "Name",
            description = "Description",
            date = LocalDate.parse("2020-02-10"),
            producer = UUID.randomUUID()
    )

    private val repository: EventRepository = mockk()

    private val service: EventService = EventService(repository)

    @BeforeEach
    fun init() {
        clearMocks(repository)
    }

    @Test
    fun findEventByIdWithCorrectIdShouldReturnEventObject() {
        val uuid = UUID.randomUUID()

        every {
            repository.findEventById(uuid)
        } returns event

        assertThat(service.findEventById(uuid)).isEqualTo(event)
    }

    @Test
    fun findEventByIdWithNonexistentIdShouldReturnResourceNotFoundException() {
        val uuid = UUID.randomUUID()

        every {
            repository.findEventById(uuid)
        } returns null

        assertThatExceptionOfType(ResourceNotFoundException::class.java)
                .isThrownBy {
                    service.findEventById(uuid)
                }
    }

    @Test
    fun findAllEventsShouldReturnListOfEvents() {
        every {
            repository.findAllEvents()
        } returns listOf()

        assertThat(service.findAllEvents()).isNotNull
    }

    @Test
    fun saveEventWithCorrectBodyShouldNotReturnEventObject() {
        every {
            repository.saveEvent(eventForm)
        } returns event

        assertThat(service.saveEvent(eventForm)).isEqualTo(event)
    }

    @Test
    fun saveEventWithNullReturnShouldReturnResourceCreateException() {
        every {
            repository.saveEvent(eventForm)
        } returns null

        assertThatExceptionOfType(ResourceCreateException::class.java)
                .isThrownBy {
                    service.saveEvent(eventForm)
                }
    }

    @Test
    fun updateEventWithCorrectBodyShouldReturnEventObject() {
        val uuid = UUID.randomUUID()

        every {
            repository.updateEvent(eventForm, uuid)
        } returns event

        assertThat(service.updateEvent(eventForm, uuid)).isEqualTo(event)
    }

    @Test
    fun updateEventWithNullReturnsShouldReturnResourceUpdateException() {
        val uuid = UUID.randomUUID()

        every {
            repository.updateEvent(eventForm, uuid)
        } returns null

        assertThatExceptionOfType(ResourceUpdateException::class.java)
                .isThrownBy {
                    service.updateEvent(eventForm, uuid)
                }
    }

    @Test
    fun deleteEventWithCorrectIdShouldNotReturnResourceNotFoundException() {
        val uuid = UUID.randomUUID()

        every {
            repository.deleteEvent(uuid)
        } returns 1

        assertDoesNotThrow {
            service.deleteEvent(uuid)
        }
    }

    @Test
    fun deleteEventWithIncorrectIdShouldReturnResourceNotFoundException() {
        val uuid = UUID.randomUUID()

        every {
            repository.deleteEvent(uuid)
        } returns 0

        assertThatExceptionOfType(ResourceNotFoundException::class.java)
                .isThrownBy {
                    service.deleteEvent(uuid)
                }
    }
}