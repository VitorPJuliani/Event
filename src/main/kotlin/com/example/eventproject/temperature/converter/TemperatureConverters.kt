package com.example.eventproject.temperature.converter

enum class TemperatureConverters : TemperatureConverter {

    KELVIN_TO_CELSIUS {
        override fun invoke(valueInKelvin: Double): Double {
            return valueInKelvin - 273.15
        }
    }
}