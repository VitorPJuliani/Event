package com.example.eventproject.converter

import kotlin.properties.Delegates

class WeatherConverter: WeatherConverterInterface {

    private var weather by Delegates.notNull<Double>()

    override fun convertKelvinToCelsius(temp: Double): Double {
        weather = temp - 273.15

        return weather
    }
}