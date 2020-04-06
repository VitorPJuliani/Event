package com.example.eventproject.service

import java.time.LocalDate

interface WeatherService {

    fun getCurrentTemperature(city: String): Double

}