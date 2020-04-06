package com.example.eventproject.service

import com.example.eventproject.form.EventForm
import com.example.eventproject.model.Event
import com.example.eventproject.model.EventResponse
import com.example.eventproject.temperature.converter.TemperatureConverter
import com.example.eventproject.temperature.formatter.TemperatureFormatter
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.UUID

class EventTemperatureImplementationTest {

    private val eventService: EventService = mockk()
    private val weatherService: WeatherService = mockk()
    private val temperatureConverter: TemperatureConverter = mockk()
    private val temperatureFormatter: TemperatureFormatter = mockk()

    private val eventTemperatureService = EventTemperatureImplementation(eventService, weatherService, temperatureConverter, temperatureFormatter)

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun findEventById() {
        val id = UUID.randomUUID()
        val producerId = UUID.randomUUID()

        val event = Event(id, "name", "description", LocalDate.parse("2020-04-05"), "city", producerId)

        val eventResponse = EventResponse(id, "name", "description", LocalDate.parse("2020-04-05"), "city", producerId, "The event weather is not available until or after the date event")

        every {
            eventService.findEventById(id)
        } returns event

        assertThat(eventTemperatureService.findEventById(id)).isEqualTo(eventResponse)
    }

    @Test
    fun findAllEvents() {
        every {
            eventService.findAllEvents()
        } returns listOf()

        assertThat(eventTemperatureService.findAllEvents()).isNotNull
    }

    @Test
    fun saveEvent() {
        val id = UUID.randomUUID()
        val producerId = UUID.randomUUID()

        val eventForm = EventForm("name" , "description", LocalDate.parse("2020-04-05"), "city", producerId)

        val event = Event(id, "name", "description", LocalDate.parse("2020-04-05"), "city", producerId)

        val eventResponse = EventResponse(id, "name", "description", LocalDate.parse("2020-04-05"), "city", producerId, "The event weather is not available until or after the date event" )

        every {
            eventService.saveEvent(eventForm)
        } returns event

        assertThat(eventTemperatureService.saveEvent(eventForm)).isEqualTo(eventResponse)
    }

    @Test
    fun updateEvent() {
        val id = UUID.randomUUID()
        val producerId = UUID.randomUUID()

        val eventForm = EventForm("name" , "description", LocalDate.parse("2020-04-05"), "city", producerId)

        val event = Event(id, "name", "description", LocalDate.parse("2020-04-04"), "city", producerId)

        val eventResponse = EventResponse(id, "name", "description", LocalDate.parse("2020-04-04"), "city", producerId, "The event weather is not available until or after the date event" )

        every {
            eventService.updateEvent(eventForm, id)
        } returns event

        assertThat(eventTemperatureService.updateEvent(eventForm, id)).isEqualTo(eventResponse)
    }
}