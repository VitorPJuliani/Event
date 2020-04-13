package com.example.eventproject.temperature.model

import com.example.eventproject.temperature.converter.TemperatureConverter
import com.example.eventproject.temperature.formatter.TemperatureFormatter

data class Temperature(private val value: Double) {

    fun convertTo(converter: TemperatureConverter): Temperature {
        return Temperature(converter.invoke(this.value))
    }

    fun formatTo(formatter: TemperatureFormatter): String {
        return formatter.formatTemperature(this)
    }

    fun asDouble(): Double {
        return this.value
    }
}