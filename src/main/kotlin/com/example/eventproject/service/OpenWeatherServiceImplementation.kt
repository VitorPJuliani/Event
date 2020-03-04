package com.example.eventproject.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.client.RestTemplate
import java.text.DecimalFormat

class OpenWeatherServiceImplementation(private val restTemplate: RestTemplate, private val apiKey: String): OpenWeatherService {

    companion object {
        private const val api: String = "https://api.openweathermap.org/data/2.5/weather"
    }

    override fun getCurrentWeather(city: String): String {

        val url = "${api}?q=${city}&appid=${apiKey}"

        val response = restTemplate.getForObject(url, String::class.java)

        val mapper = ObjectMapper()
        val jsonResponse = mapper.readTree(response)

        val temp = jsonResponse.get("main").get("temp").doubleValue()

        val celsius = convertKelvinToCelsius(temp)

        return DecimalFormat("#.##").format(celsius)
    }

    private fun convertKelvinToCelsius(temp: Double): Double {
        return temp - 273.15
    }
}