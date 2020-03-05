package com.example.eventproject.service

import com.example.eventproject.properties.AppProperties
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.client.RestTemplate
import java.sql.Timestamp
import java.text.DecimalFormat
import java.time.LocalDate

class OpenWeatherServiceImplementation(private val restTemplate: RestTemplate, private val openWeather: AppProperties.OpenWeather): OpenWeatherService {

    override fun getCurrentWeatherInCelsius(city: String): String {

        val url = "${openWeather.url}?q=${city}&appid=${openWeather.apiKey}"

        val response = restTemplate.getForObject(url, String::class.java)

        val mapper = ObjectMapper()
        val jsonResponse = mapper.readTree(response)

        val temp = jsonResponse.get("main").get("temp").doubleValue()

        val celsius = convertKelvinToCelsius(temp)

        return "${DecimalFormat("#.##").format(celsius)}ºC"
    }

    override fun getPastWeatherInCelsius(city: String, date: LocalDate): String {
        val timeInTimeStamp: Timestamp = Timestamp.valueOf(date.atStartOfDay())

        return timeInTimeStamp.toString()
    }

    private fun convertKelvinToCelsius(temp: Double): Double {
        return temp - 273.15
    }
}