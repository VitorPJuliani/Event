package com.example.eventproject.controller

import com.example.eventproject.exception.ResourceCreateException
import com.example.eventproject.exception.ResourceNotFoundException
import com.example.eventproject.exception.ResourceUpdateException
import com.example.eventproject.form.EventForm
import com.example.eventproject.model.Event
import com.example.eventproject.service.EventService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import java.time.LocalDate
import java.util.UUID

@WebMvcTest(EventController::class)
internal class EventControllerTest(@Autowired private val mockMvc: MockMvc,
                                   @Autowired private val objectMapper: ObjectMapper) {

    @MockkBean
    private lateinit var service: EventService

    @Test
    fun `get all events should return 200`() {

        val events = listOf(
                Event(UUID.randomUUID(), "name", "description", LocalDate.parse("2020-02-10"), UUID.randomUUID()),
                Event(UUID.randomUUID(), "name 2", "description 2", LocalDate.parse("2020-02-10"), UUID.randomUUID()),
                Event(UUID.randomUUID(), "name 3", "description 3", LocalDate.parse("2020-02-10"), UUID.randomUUID())
        )

        every {
            service.findAllEvents()
        } returns events

        mockMvc.perform(get("/events"))
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

        mockMvc.perform(get("/events/$uuid"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(event.id.toString()))
                .andExpect(jsonPath("$.name").value(event.name))
    }

    @Test
    fun `get event by id when id is nonexistent should return 404`() {

        val uuid = UUID.randomUUID()

        every {
            service.findEventById(uuid)
        } throws ResourceNotFoundException("Invalid Id")

        mockMvc.perform(get("/events/{id}", uuid))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `post event should return 200`() {

        val producerUuid = UUID.randomUUID()

        val node = objectMapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("description", "description")
            put("date", "2020-02-10")
            put("producer", producerUuid.toString())
        }

        val eventForm = EventForm(node)

        val event = Event(
                id = UUID.randomUUID(),
                name = "name",
                description = "description",
                date = LocalDate.parse("2020-02-10"),
                producer = producerUuid
        )

        every {
            service.saveEvent(eventForm)
        } returns event

        mockMvc.perform(post("/events")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(node)))
                .andExpect(status().isCreated)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `post event when body is incorrect should return 400`() {

        val node = this.objectMapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("description", "description")
            put("date", "2020-02-10")
        }

        mockMvc.perform(post("/events")
                .content(objectMapper.writeValueAsString(node))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `post event when serive returns null should return 400`() {

        val node = objectMapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("description", "description")
            put("date", "2020-02-10")
            put("producer", UUID.randomUUID().toString())
        }

        val event = EventForm(node)

        every {
            service.saveEvent(event)
        } throws ResourceCreateException("Creating error")

        mockMvc.perform(post("/events")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(node)))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `put event should return 200`() {

        val uuid = UUID.randomUUID()

        val producerUuid = UUID.randomUUID()

        val node = objectMapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("description", "description")
            put("date", "2020-02-10")
            put("producer", producerUuid.toString())
        }

        val eventForm = EventForm(node)

        val event = Event(
                id = uuid,
                name = "name",
                description = "description",
                date = LocalDate.parse("2020-02-10"),
                producer = producerUuid
        )

        every {
            service.updateEvent(eventForm, uuid)
        } returns event

        mockMvc.perform(put("/events/{id}", uuid)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(node)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(event.name))
    }

    @Test
    fun `put event when body is incorrect should return 400`() {

        val node = this.objectMapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("description", "description")
            put("date", "2020-02-10")
        }

        mockMvc.perform(put("/events/{id}", UUID.randomUUID())
                .content(objectMapper.writeValueAsString(node))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `put event when service returns null should return 400`() {

        val uuid = UUID.randomUUID()

        val node = objectMapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("description", "description")
            put("date", "2020-02-10")
            put("producer", UUID.randomUUID().toString())
        }

        val event = EventForm(node)

        every {
            service.updateEvent(event, uuid)
        } throws ResourceUpdateException("Updating error")

        mockMvc.perform(put("/events/{id}", uuid)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(node)))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `delete event should return 200`() {

        val uuid = UUID.randomUUID()

        every {
            service.deleteEvent(uuid)
        } returns Unit

        mockMvc.perform(delete("/events/$uuid"))
                .andExpect(status().isNoContent)
    }

    @Test
    fun `delete event when id is nonexistent should return 404`() {

        val uuid = UUID.randomUUID()

        every {
            service.deleteEvent(uuid)
        } throws ResourceNotFoundException("not found id $uuid")

        mockMvc.perform(delete("/events/$uuid"))
                .andExpect(status().isNotFound)
    }
}