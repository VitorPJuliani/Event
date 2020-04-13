package com.example.eventproject.service

import com.example.eventproject.form.EventForm
import com.example.eventproject.model.Event
import com.example.eventproject.model.EventResponse
import com.example.eventproject.temperature.converter.TemperatureConverter
import com.example.eventproject.temperature.formatter.TemperatureFormatter
import com.example.eventproject.temperature.model.Temperature
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

        val event = Event(id, "name", "description", LocalDate.now(), "city", producerId)
        val eventWithoutTemperature = EventResponse(event)

        val eventResponse = EventResponse(id, "name", "description", LocalDate.now(), "city", producerId, "12.90°C")

        every {
            eventService.findEventById(id)
        } returns eventWithoutTemperature

        every {
            weatherService.getCurrentTemperatureInKelvinByCity("city")
        } returns 128.5

        every {
            temperatureConverter.invoke(128.5)
        } returns 12.9

        every {
            temperatureFormatter.formatTemperature(Temperature(12.9))
        } returns "12.90°C"

        assertThat(eventTemperatureService.findEventById(id)).isEqualTo(eventResponse)
    }

    @Test
    fun `find event by id when event date is not today`() {
        val id = UUID.randomUUID()
        val producerId = UUID.randomUUID()

        val event = Event(id, "name", "description", LocalDate.parse("2020-04-07"), "city", producerId)
        val eventWithoutTemperature = EventResponse(event)

        val eventResponse = EventResponse(id, "name", "description", LocalDate.parse("2020-04-07"), "city", producerId, "The event temperature is not available until or after the date event")

        every {
            eventService.findEventById(id)
        } returns eventWithoutTemperature

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

        val eventForm = EventForm("name" , "description", LocalDate.now(), "city", producerId)

        val event = Event(id, "name", "description", LocalDate.now(), "city", producerId)
        val eventWithoutTemperature = EventResponse(event)

        val eventResponse = EventResponse(id, "name", "description", LocalDate.now(), "city", producerId, "12.90°C")

        every {
            eventService.saveEvent(eventForm)
        } returns eventWithoutTemperature

        every {
            weatherService.getCurrentTemperatureInKelvinByCity("city")
        } returns 128.5

        every {
            temperatureConverter.invoke(128.5)
        } returns 12.9

        every {
            temperatureFormatter.formatTemperature(Temperature(12.9))
        } returns "12.90°C"

        assertThat(eventTemperatureService.saveEvent(eventForm)).isEqualTo(eventResponse)
    }

    @Test
    fun `save event when event date is not today`() {
        val id = UUID.randomUUID()
        val producerId = UUID.randomUUID()

        val eventForm = EventForm("name" , "description", LocalDate.parse("2020-04-05"), "city", producerId)

        val event = Event(id, "name", "description", LocalDate.parse("2020-04-05"), "city", producerId)
        val eventWithoutTemperature = EventResponse(event)

        val eventResponse = EventResponse(id, "name", "description", LocalDate.parse("2020-04-05"), "city", producerId, "The event temperature is not available until or after the date event" )

        every {
            eventService.saveEvent(eventForm)
        } returns eventWithoutTemperature

        assertThat(eventTemperatureService.saveEvent(eventForm)).isEqualTo(eventResponse)
    }

    @Test
    fun updateEvent() {
        val id = UUID.randomUUID()
        val producerId = UUID.randomUUID()

        val eventForm = EventForm("name" , "description", LocalDate.now(), "city", producerId)

        val event = Event(id, "name", "description", LocalDate.now(), "city", producerId)
        val eventWithoutTemperature = EventResponse(event)

        val eventResponse = EventResponse(id, "name", "description", LocalDate.now(), "city", producerId, "12.90°C")

        every {
            eventService.updateEvent(eventForm, id)
        } returns eventWithoutTemperature

        every {
            weatherService.getCurrentTemperatureInKelvinByCity("city")
        } returns 128.5

        every {
            temperatureConverter.invoke(128.5)
        } returns 12.9

        every {
            temperatureFormatter.formatTemperature(Temperature(12.9))
        } returns "12.90°C"

        assertThat(eventTemperatureService.updateEvent(eventForm, id)).isEqualTo(eventResponse)
    }

    @Test
    fun `update event when event date is not today`() {
        val id = UUID.randomUUID()
        val producerId = UUID.randomUUID()

        val eventForm = EventForm("name" , "description", LocalDate.parse("2020-04-05"), "city", producerId)

        val event = Event(id, "name", "description", LocalDate.parse("2020-04-04"), "city", producerId)
        val eventWithoutTemperature = EventResponse(event)

        val eventResponse = EventResponse(id, "name", "description", LocalDate.parse("2020-04-04"), "city", producerId, "The event temperature is not available until or after the date event" )

        every {
            eventService.updateEvent(eventForm, id)
        } returns eventWithoutTemperature

        assertThat(eventTemperatureService.updateEvent(eventForm, id)).isEqualTo(eventResponse)
    }
}