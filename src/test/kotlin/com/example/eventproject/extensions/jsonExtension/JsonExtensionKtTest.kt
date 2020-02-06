package com.example.eventproject.extensions.jsonExtension

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest

internal class JsonExtensionKtTest {

    private val expectedJson = listOf<String>("name", "document", "email")

    @Test
    fun checkJsonErrorsWithCorrectJsonShouldNotReturnException() {
        val mapper = ObjectMapper()
        val node = mapper.createObjectNode()

        node.apply {
            put("name", "vitor")
            put("document", "123456")
            put("email", "vitortest@email.com")
        }

        assertDoesNotThrow {
            node.checkForJsonFieldErrors(expectedJson)
        }
    }

    @Test
    fun checkJsonErrorsWithMissingFieldsShouldReturnException() {
        val mapper = ObjectMapper()
        val node = mapper.createObjectNode()

        node.apply {
            put("document", "123456")
            put("email", "vitortest@email.com")
        }

        assertThrows<IllegalArgumentException> {
            node.checkForJsonFieldErrors(expectedJson)
        }
    }
}