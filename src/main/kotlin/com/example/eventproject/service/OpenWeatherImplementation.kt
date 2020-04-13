package com.example.eventproject.service

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

class OpenWeatherImplementation(private val restTemplate: RestTemplate, private val url: String): WeatherService {

    override fun getCurrentTemperatureInKelvinByCity(city: String): Double {
        val response = restTemplate.getForObject(url, JsonNode::class.java, city)

        return response?.get("main")?.get("temp")?.doubleValue() ?: throw HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Api error")
    }
}