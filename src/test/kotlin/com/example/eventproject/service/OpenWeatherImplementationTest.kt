package com.example.eventproject.service

import com.example.eventproject.properties.AppProperties
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper

internal class OpenWeatherImplementationTest {

    private val restTemplate: RestTemplate = mockk()
    private val openWeather: AppProperties.OpenWeather = mockk()

    private val openWeatherService = OpenWeatherImplementation(restTemplate, openWeather)

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun test() {

        val url = "https://api.openweathermap.org/data/2.5/weather?q=Campinas&appid=8971275617261ihsuay81w"

        val mapper = ObjectMapper()
        val node = mapper.createObjectNode()

        node.apply {
            putObject("main").put("temp", 278)
        }

        every {
            openWeather.url
        } returns "https://api.openweathermap.org/data/2.5/weather"

        every {
            openWeather.apiKey
        } returns "8971275617261ihsuay81w"

        every {
            restTemplate.getForObject(url, String::class.java)
        } returns node.toString()

        assertThat(openWeatherService.getCurrentWeather("Campinas")).isEqualTo(278.0)
    }
}