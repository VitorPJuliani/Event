package com.example.eventproject.form

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import java.time.format.DateTimeParseException

internal class EventFormTest {

    @Test
    fun eventFormConstructorWithCorrectBodyShouldNotReturnException() {
        val mapper = ObjectMapper()
        val node = mapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("description", "description")
            put("date", "2020-02-06")
            put("producer", "4fb5c1d4-576d-497f-9eb4-94fb0796b4f9")
        }

        assertDoesNotThrow {
            EventForm(node)
        }
    }

    @Test
    fun eventFormConstructorWithMissingFieldsBodyShouldReturnException() {
        val mapper = ObjectMapper()
        val node = mapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("description", "description")
            put("date", "2020-02-06")
        }

        assertThrows<IllegalArgumentException> {
            EventForm(node)
        }
    }

    @Test
    fun eventFormConstructorWithDateErrorFormatShouldReturnException() {
        val mapper = ObjectMapper()
        val node = mapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("description", "description")
            put("date", "")
            put("producer", "4fb5c1d4-576d-497f-9eb4-94fb0796b4f9")
        }

        assertThrows<DateTimeParseException> {
            EventForm(node)
        }
    }

    @Test
    fun eventFormConstructorWithUuidErrorFormatShouldReturnException() {
        val mapper = ObjectMapper()
        val node = mapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("description", "description")
            put("date", "2020-02-06")
            put("producer", "")
        }

        assertThrows<IllegalArgumentException> {
            EventForm(node)
        }
    }

    @Test
    fun eventFormConstructorWithEmpyFieldsShouldReturnException() {
        val mapper = ObjectMapper()
        val node = mapper.createObjectNode()

        node.apply {
            put("name", "")
            put("description", "")
            put("date", "2020-02-06")
            put("producer", "4fb5c1d4-576d-497f-9eb4-94fb0796b4f9")
        }

        assertThrows<IllegalArgumentException> {
            EventForm(node)
        }
    }
}