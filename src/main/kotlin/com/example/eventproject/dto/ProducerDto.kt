package com.example.eventproject.dto

data class ProducerDto(
        val name: String,
        val email: String,
        val document: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProducerDto

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