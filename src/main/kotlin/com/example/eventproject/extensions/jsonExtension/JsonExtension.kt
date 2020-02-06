package com.example.eventproject.extensions.jsonExtension

import com.fasterxml.jackson.databind.JsonNode

fun JsonNode.checkForJsonFieldErrors(expectedJson: List<String>) {

    val missingFields: MutableList<String> = mutableListOf()

    expectedJson.forEach {
        if (!this.has(it))
            missingFields.add(it)
    }

    if (missingFields.isNotEmpty())
        throw IllegalArgumentException("Missing fields: $missingFields")
}