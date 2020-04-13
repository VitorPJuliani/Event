package com.example.eventproject.service

interface WeatherService {

    fun getCurrentTemperatureInKelvinByCity(city: String): Double

}