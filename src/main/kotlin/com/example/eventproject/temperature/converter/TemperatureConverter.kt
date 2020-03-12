package com.example.eventproject.temperature.converter

import com.example.eventproject.temperature.model.Temperature

interface TemperatureConverter {

    fun convertTemperature(temperature: Temperature): Temperature
}