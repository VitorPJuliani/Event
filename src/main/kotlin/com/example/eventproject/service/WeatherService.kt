package com.example.eventproject.service

import java.time.LocalDate

interface WeatherService {

    fun getCurrentWeather(city: String): Double

}