package com.example.eventproject.temperature.formatter

import com.example.eventproject.temperature.model.Temperature
import java.text.DecimalFormat

enum class TemperatureFormatters : TemperatureFormatter {

    CELSIUS_FORMAT {
        override fun formatTemperature(temperature: Temperature): String {
            return DecimalFormat("#.##°C").format(temperature.asDouble())
        }
    }
}