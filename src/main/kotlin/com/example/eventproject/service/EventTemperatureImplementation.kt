package com.example.eventproject.service

import com.example.eventproject.form.EventForm
import com.example.eventproject.model.Event
import com.example.eventproject.model.EventResponse
import com.example.eventproject.temperature.converter.TemperatureConverter
import com.example.eventproject.temperature.formatter.TemperatureFormatter
import com.example.eventproject.temperature.model.Temperature
import java.time.LocalDate
import java.util.UUID

class EventTemperatureImplementation(private val eventService: EventService,
                                     private val weatherService: WeatherService,
                                     private val temperatureConverter: TemperatureConverter,
                                     private val temperatureFormatter: TemperatureFormatter) : EventTemperatureService {

    override fun findEventById(id: UUID): EventResponse {
        val event = eventService.findEventById(id)

        return buildEventResponse(event)
    }

    override fun findAllEvents(): List<EventResponse> {
        val events = eventService.findAllEvents()

        val listOfEventsResponse = mutableListOf<EventResponse>()

        if (events.isNotEmpty()) {
            events.listIterator().forEach {
                listOfEventsResponse.add(buildEventResponse(it))
            }
        }

        return listOfEventsResponse
    }

    override fun saveEvent(event: EventForm): EventResponse {
        val savedEvent = eventService.saveEvent(event)

        return buildEventResponse(savedEvent)
    }

    override fun updateEvent(event: EventForm, id: UUID): EventResponse {
        val updatedEvent = eventService.updateEvent(event, id)

        return buildEventResponse(updatedEvent)
    }

    override fun deleteEvent(id: UUID) {
        return eventService.deleteEvent(id)
    }

    private fun buildEventResponse(event: Event): EventResponse {
        val currentDate = LocalDate.now()

        val weather = when {
            currentDate.isEqual(event.date) -> {
                val currentTemperature = weatherService.getCurrentWeather(event.city)

                val temperature = Temperature(currentTemperature)

                val temperatureInCelsius = temperature.convertTo(temperatureConverter)

                val temperatureFormatted = temperatureInCelsius.formatTo(temperatureFormatter)

                temperatureFormatted.toString()
            }
            else -> "The event weather is not available until or after the date event"
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