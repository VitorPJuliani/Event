package com.example.eventproject.controller

import com.example.eventproject.model.Event
import com.example.eventproject.service.EventService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.time.LocalDate
import java.util.UUID

@WebMvcTest(EventController::class)
internal class EventControllerTest(@Autowired
                                   private val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var service: EventService

    @Test
    fun `get all events should return 200`() {

        val events = listOf<Event>(
                Event(UUID.randomUUID(), "name", "description", LocalDate.parse("2020-02-10"), UUID.randomUUID()),
                Event(UUID.randomUUID(), "name 2", "description 2", LocalDate.parse("2020-02-10"), UUID.randomUUID()),
                Event(UUID.randomUUID(), "name 3", "description 3", LocalDate.parse("2020-02-10"), UUID.randomUUID())
        )

        every {
            service.findAllEvents()
        } returns events

        this.mockMvc.perform(get("/events"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray)
                .andExpect(jsonPath("$", hasSize<Any>(3)))
                .andExpect(jsonPath("$[0].id").value(events[0].id.toString()))
                .andExpect(jsonPath("$[1].name").value(events[1].name))
                .andExpect(jsonPath("$[2].description").value(events[2].description))
    }
}