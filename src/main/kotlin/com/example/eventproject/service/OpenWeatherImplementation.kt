package com.example.eventproject.service

import com.example.eventproject.properties.AppProperties
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.client.RestTemplate

class OpenWeatherImplementation(private val restTemplate: RestTemplate, private val openWeather: AppProperties.OpenWeather): WeatherService {

    override fun getCurrentWeather(city: String): Double {
        val url = "${openWeather.url}?q=${city}&appid=${openWeather.apiKey}"

        val response = restTemplate.getForObject(url, String::class.java)

        val mapper = ObjectMapper()
        val jsonResponse = mapper.readTree(response)

        return jsonResponse.get("main").get("temp").doubleValue()
    }
}