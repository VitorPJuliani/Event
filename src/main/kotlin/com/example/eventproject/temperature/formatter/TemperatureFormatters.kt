package com.example.eventproject.temperature.formatter

import com.example.eventproject.temperature.model.Temperature
import java.text.DecimalFormat

enum class TemperatureFormatters : TemperatureFormatter {

    DOUBLE_DECIMAL {
        override fun formatTemperature(temperature: Temperature): Double {
            return DecimalFormat("#.##").format(temperature.asDouble()).toDouble()
        }
    }
}