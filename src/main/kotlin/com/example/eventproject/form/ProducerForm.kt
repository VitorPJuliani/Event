package com.example.eventproject.form

import com.example.eventproject.exception.ResourceCreateException
import com.fasterxml.jackson.databind.JsonNode

data class ProducerForm(
        val name: String,
        val email: String,
        val document: String
) {

    companion object {
        operator fun invoke(data: JsonNode): ProducerForm {

            val errors: MutableList<String> = mutableListOf<String>()

            if (!data.has("name"))
                errors.add("name")
            if (!data.has("email"))
                errors.add("email")
            if (!data.has("document"))
                errors.add("document")

            if (errors.isEmpty())
                return ProducerForm(data["name"].textValue(), data["email"].textValue(), data["document"].textValue())
            else
                throw ResourceCreateException("Missing fields: $errors")
        }
    }

    init {
        if(name.isEmpty() || email.isEmpty() || document.isEmpty())
            throw ResourceCreateException("The required fields can not be empty")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProducerForm

        if (name != other.name) return false
        if (email != other.email) return false
        if (document != other.document) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + document.hashCode()
        return result
    }

    override fun toString(): String {
        return "ProducerDto(name='$name', email='$email', document='$document')"
    }
}