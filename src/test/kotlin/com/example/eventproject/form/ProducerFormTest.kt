package com.example.eventproject.form

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

internal class ProducerFormTest {

    @Test
    fun producerFormConstructorWithCorrectBodyShouldNotThrowException() {
        val mapper = ObjectMapper()
        val node = mapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("email", "email@email.com")
            put("document", "123456")
        }

        assertDoesNotThrow {
            ProducerForm(node)
        }
    }

    @Test
    fun producerFormConstructorWithMissingFieldsShouldThrowException() {
        val mapper = ObjectMapper()
        val node = mapper.createObjectNode()

        node.apply {
            put("email", "email")
            put("document", "123456")
        }

        assertThrows<IllegalArgumentException> {
            ProducerForm(node)
        }
    }

    @Test
    fun producerFormConstructorWithEmptyFieldsShouldThrowException() {
        val mapper = ObjectMapper()
        val node = mapper.createObjectNode()

        node.apply {
            put("name", "")
            put("email", "")
            put("document", "123456")
        }

        assertThrows<IllegalArgumentException> {
            ProducerForm(node)
        }
    }

    @Test
    fun producerFormConstructorWithEmailErrorFormatShouldThrowException() {
        val mapper = ObjectMapper()
        val node = mapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("email", "@vitor")
            put("document", "123456")
        }

        assertThrows<IllegalArgumentException> {
            ProducerForm(node)
        }
    }
}