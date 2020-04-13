package com.example.eventproject.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate

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
        val apiResponse = JsonNodeFactory.instance.objectNode()
        apiResponse.set<ObjectNode>("main", JsonNodeFactory.instance.objectNode().put("temp", 278))

        every {
            restTemplate.getForObject(url, JsonNode::class.java, "Campinas")
        } returns apiResponse

        assertThat(openWeatherService.getCurrentTemperatureInKelvinByCity("Campinas")).isEqualTo(278.0)
    }
}