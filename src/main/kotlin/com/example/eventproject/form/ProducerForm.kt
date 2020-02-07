package com.example.eventproject.form

import com.example.eventproject.extensions.jsonExtension.checkForJsonFieldErrors
import com.fasterxml.jackson.databind.JsonNode
import java.util.regex.Pattern

data class ProducerForm(
        val name: String,
        val email: String,
        val document: String
) {
    companion object {
        operator fun invoke(data: JsonNode): ProducerForm {
            val expectedJson: List<String> = listOf("name", "email", "document")

            data.checkForJsonFieldErrors(expectedJson)

            return ProducerForm(data["name"].textValue(), data["email"].textValue(), data["document"].textValue())
        }
    }

    init {
        if (this.name.isEmpty() || this.email.isEmpty() || this.document.isEmpty())
            throw IllegalArgumentException("The required fields can not be empty")

        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)\$"

        if (!Pattern.compile(emailRegex).matcher(this.email).matches()) {
            throw IllegalArgumentException("Field email has a wrong format")
        }
    }
}