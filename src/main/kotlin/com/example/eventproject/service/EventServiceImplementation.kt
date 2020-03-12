package com.example.eventproject.service

import com.example.eventproject.exception.ResourceCreateException
import com.example.eventproject.exception.ResourceNotFoundException
import com.example.eventproject.exception.ResourceUpdateException
import com.example.eventproject.form.EventForm
import com.example.eventproject.model.Event
import com.example.eventproject.model.EventResponse
import com.example.eventproject.repository.EventRepository
import com.example.eventproject.temperature.converter.TemperatureConverter
import com.example.eventproject.temperature.formatter.TemperatureFormatter
import com.example.eventproject.temperature.model.Temperature
import java.time.LocalDate
import java.util.UUID

class EventServiceImplementation(private val eventRepository: EventRepository,
                                 private val weatherService: WeatherService,
                                 private val temperatureConverter: TemperatureConverter,
                                 private val temperatureFormatter: TemperatureFormatter) : EventService {

    override fun findEventById(id: UUID): EventResponse {
        val event = eventRepository.findEventById(id) ?: throw ResourceNotFoundException("Not found event with id: $id")

        return buildEventResponse(event)
    }

    override fun findAllEvents(): List<EventResponse> {
        val events = eventRepository.findAllEvents()

        val eventsResponse: MutableList<EventResponse> = mutableListOf()

        if (events.isNotEmpty()) {
            events.listIterator().forEach {
                val event = buildEventResponse(it)
                eventsResponse.add(event)
            }
        }

        return eventsResponse
    }

    override fun saveEvent(event: EventForm): EventResponse {
        val savedEvent = eventRepository.saveEvent(event) ?: throw ResourceCreateException("Creating error")

        return buildEventResponse(savedEvent)
    }

    override fun updateEvent(event: EventForm, id: UUID): EventResponse {
        val updatedEvent = eventRepository.updateEvent(event, id) ?: throw ResourceUpdateException("Updating error")

        return buildEventResponse(updatedEvent)
    }

    override fun deleteEvent(id: UUID) {
        if (eventRepository.deleteEvent(id) == 0)
            throw ResourceNotFoundException("Not found event with id: $id")
    }

    private fun buildEventResponse(event: Event): EventResponse {
        val currentDate = LocalDate.now()

        val weather = when {
            currentDate.isEqual(event.date) -> {
                val currentTemperature = weatherService.getCurrentWeather(event.city)

                val temperature = Temperature(currentTemperature)

                val temperatureInCelsius = temperature.convertTo(temperatureConverter)

                val temperatureFormatted = temperatureInCelsius.formatTo(temperatureFormatter)

                "${temperatureFormatted}ºC"
            }
            else -> "The event weather is not available until the event date"
        }

        return EventResponse(
                id = event.id,
                name = event.name,
                description = event.description,
                date = event.date,
                city = event.city,
                producer = event.producer,
                weather = weather
        )
    }
}