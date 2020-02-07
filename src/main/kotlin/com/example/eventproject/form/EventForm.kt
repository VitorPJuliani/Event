package com.example.eventproject.form

import com.example.eventproject.extensions.jsonExtension.checkForJsonFieldErrors
import com.fasterxml.jackson.databind.JsonNode
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*

data class EventForm(
        val name: String,
        val description: String,
        val date: LocalDate,
        val producer: UUID
) {
    companion object {
        operator fun invoke(data: JsonNode): EventForm {
            val expectedJson = listOf<String>("name", "description", "date", "producer")

            data.checkForJsonFieldErrors(expectedJson)

            try {
                return EventForm(data["name"].textValue(),
                        data["description"].textValue(),
                        LocalDate.parse(data["date"].textValue()),
                        UUID.fromString(data["producer"].textValue()))
            } catch (e: DateTimeParseException) {
                throw DateTimeParseException("Field date has a wrong format or is empty", data["date"].textValue(), 1)
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Field producer has a wrong format or is empty")
            }
        }
    }

    init {
        if (this.name.isEmpty() || this.description.isEmpty())
            throw IllegalArgumentException("The required fields can not be empty")
    }
}
