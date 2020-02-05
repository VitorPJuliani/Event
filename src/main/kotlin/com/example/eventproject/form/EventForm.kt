package com.example.eventproject.form

import com.example.eventproject.extensions.jsonExtension.checkForJsonFieldErrors
import com.fasterxml.jackson.databind.JsonNode
import java.time.LocalDate
import java.util.UUID

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

            return EventForm(data["name"].textValue(),
                    data["description"].textValue(),
                    LocalDate.parse(data["date"].textValue()),
                    UUID.fromString(data["producer"].textValue()))
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EventForm

        if (name != other.name) return false
        if (description != other.description) return false
        if (date != other.date) return false
        if (producer != other.producer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + producer.hashCode()
        return result
    }

    override fun toString(): String {
        return "EventForm(name='$name', description='$description', date=$date, producer=$producer)"
    }
}
