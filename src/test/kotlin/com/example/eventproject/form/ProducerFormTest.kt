package com.example.eventproject.form

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

internal class ProducerFormTest {

    @Test
    fun `producer form constructor when fields are correct should not throw exception`() {
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
    fun `producer form constructor when missing fields should throw IllegalArgumentException`() {
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
    fun `producer form constructor when empty fields should throw IllegalArgumentException`() {
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
    fun `producer form constructor when wrong email format should throw IllegalArgumentException`() {
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