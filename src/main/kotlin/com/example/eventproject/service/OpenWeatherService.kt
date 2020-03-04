package com.example.eventproject.service

interface OpenWeatherService {

    fun getCurrentWeather(city: String): String
}