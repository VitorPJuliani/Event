package com.example.eventproject.controller

import com.example.eventproject.exception.ResourceNotFoundException
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

    @Test
    fun `get event by id when id exists should return 200`() {

        val uuid = UUID.randomUUID()

        val event = Event(
                id = uuid,
                name = "name",
                description = "description",
                date = LocalDate.parse("2020-02-10"),
                producer = UUID.randomUUID()
        )

        every {
            service.findEventById(uuid)
        } returns event

        this.mockMvc.perform(get("/events/$uuid"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(event.id.toString()))
                .andExpect(jsonPath("$.name").value(event.name))
    }

    @Test
    fun `get event by id when id is nonexistent should return 400`() {

        val uuid = UUID.randomUUID()

        every {
            service.findEventById(uuid)
        } throws ResourceNotFoundException("Invalid Id")

        this.mockMvc.perform(get("/{id}", uuid))
                .andExpect(status().isNotFound)
    }
}