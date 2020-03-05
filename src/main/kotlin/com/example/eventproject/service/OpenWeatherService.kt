package com.example.eventproject.service

import java.time.LocalDate

interface OpenWeatherService {

    fun getCurrentWeatherInCelsius(city: String): String

    fun getPastWeatherInCelsius(city: String, date: LocalDate): String
}