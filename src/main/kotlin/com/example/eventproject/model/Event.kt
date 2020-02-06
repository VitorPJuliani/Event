package com.example.eventproject.model

import java.time.LocalDate
import java.util.UUID

data class Event(
        val id: UUID,
        val name: String,
        val description: String,
        val date: LocalDate,
        val producer: UUID
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (date != other.date) return false
        if (producer != other.producer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + producer.hashCode()
        return result
    }

    override fun toString(): String {
        return "Event(id=$id, name='$name', description='$description', date=$date, producer=$producer)"
    }


}