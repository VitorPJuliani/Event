package com.example.eventproject.temperature.converter

import com.example.eventproject.temperature.model.Temperature
import kotlin.properties.Delegates

class KelvinToCelsius: TemperatureConverter {

    private var temperature by Delegates.notNull<Double>()

    override fun convertTemperature(temperature: Temperature): Temperature {
        val valueInKelvin = temperature.asDouble()

        this.temperature = valueInKelvin - 273.15

        return Temperature(this.temperature)
    }

}