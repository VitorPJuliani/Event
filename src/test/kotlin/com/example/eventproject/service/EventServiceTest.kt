package com.example.eventproject.service

import com.example.eventproject.exception.ResourceNotFoundException
import com.example.eventproject.model.Event
import com.example.eventproject.repository.EventRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*

internal class EventServiceTest {

    private val repository: EventRepository = mockk()

    private val service: EventService = EventService(repository)

    @Test
    fun findEventByIdWithCorrectIdShouldReturnEventObject() {
        val uuid = UUID.randomUUID()

        val event = Event(
                uuid,
                "Evento teste do vitor",
                "Espero que esse teste funcione de primeira",
                LocalDate.parse("2020-02-07"),
                UUID.fromString("cd37f2ee-c931-4efe-abc3-9050f74324e3")
        )

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
}