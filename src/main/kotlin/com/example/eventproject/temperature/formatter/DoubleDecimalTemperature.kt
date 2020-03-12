package com.example.eventproject.temperature.formatter

import com.example.eventproject.temperature.model.Temperature
import java.text.DecimalFormat

class DoubleDecimalTemperature: TemperatureFormatter {

    private lateinit var temperature: String

    override fun formatTemperature(temperature: Temperature): String {
        this.temperature = DecimalFormat("#.##").format(temperature.asDouble())

        return this.temperature
    }
}