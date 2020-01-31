package com.example.eventproject.form

import java.lang.NullPointerException

data class ProducerForm(
        val name: String,
        val email: String,
        val document: String
) {

    init {
        if (name.isNullOrEmpty())
            throw NullPointerException("Name cannot be empty")
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