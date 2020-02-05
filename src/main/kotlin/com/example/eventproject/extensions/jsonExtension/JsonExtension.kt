package com.example.eventproject.extensions.jsonExtension

import com.fasterxml.jackson.databind.JsonNode

fun JsonNode.checkJsonErrors(expectedJson: List<String>): List<String> {

    val missingFields: MutableList<String> = mutableListOf()

    expectedJson.forEach {
        if (!this.has(it))
            missingFields.add(it)
    }

    return missingFields
}