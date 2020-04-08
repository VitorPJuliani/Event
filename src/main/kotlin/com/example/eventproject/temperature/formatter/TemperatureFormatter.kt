package com.example.eventproject.temperature.formatter

import com.example.eventproject.temperature.model.Temperature

interface TemperatureFormatter {

    fun formatTemperature(temperature: Temperature): String
}