//package com.example.eventproject.service
//
//import com.example.eventproject.properties.AppProperties
//import io.mockk.clearAllMocks
//import io.mockk.every
//import io.mockk.mockk
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.springframework.web.client.RestTemplate
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
//
//internal class OpenWeatherServiceImplementationTest {
//
//    private val restTemplate: RestTemplate = mockk()
//    private val appProperties: AppProperties = mockk()
//
//    private val openWeatherService = OpenWeatherServiceImplementation(restTemplate, appProperties.openweather.apiKey)
//
//    @BeforeEach
//    fun setup() {
//        clearAllMocks()
//    }
//
//    @Test
//    fun test() {
//
//        val url = "https://api.openweathermap.org/data/2.5/weather?q=Campinas&appid=0f2751a04861f6a16a65c4de8a0957d5"
//
//        val mapper = ObjectMapper()
//        val node = mapper.createObjectNode()
//
//        node.apply {
//            putObject("main").put("temp", 278)
//        }
//
//        every {
//            restTemplate.getForObject(url, String::class.java)
//        } returns node.toString()
//
//        assertThat(openWeatherService.getCurrentWeather("Campinas")).isEqualTo("4.85")
//    }
//}