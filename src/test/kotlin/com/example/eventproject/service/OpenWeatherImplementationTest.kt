package com.example.eventproject.service

import com.example.eventproject.properties.AppProperties
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.RestTemplate
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper

internal class OpenWeatherImplementationTest {

    private val restTemplate: RestTemplate = mockk()
    private val url: String = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid=8971275617261ihsuay81w"
    private val openWeatherService = OpenWeatherImplementation(restTemplate, url)

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun getCurrentWeather() {

        val mapper = ObjectMapper()
        val node = mapper.createObjectNode()

        node.apply {
            putObject("main").put("temp", 278)
        }

        every {
            restTemplate.getForObject(any(), eq(JsonNode::class.java), any())
        } returns node

        assertThat(openWeatherService.getCurrentTemperatureInKelvinByCity("Campinas")).isEqualTo(278.0)
    }
}