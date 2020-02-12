package com.example.eventproject.controller

import com.example.eventproject.exception.ResourceCreateException
import com.example.eventproject.exception.ResourceNotFoundException
import com.example.eventproject.exception.ResourceUpdateException
import com.example.eventproject.form.ProducerForm
import com.example.eventproject.model.Producer
import com.example.eventproject.service.ProducerService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import java.util.UUID

@WebMvcTest(ProducerController::class)
internal class ProducerControllerTest(@Autowired private val mockMvc: MockMvc,
                                      @Autowired private val objectMapper: ObjectMapper) {

    @MockkBean
    private lateinit var service: ProducerService

    @Test
    fun `get all producers should return 200`() {

        val producers = listOf<Producer>(
                Producer(UUID.randomUUID(), "name", "email@email.com", "123456"),
                Producer(UUID.randomUUID(), "name 2", "email2@email.com", "1234567"),
                Producer(UUID.randomUUID(), "name 3", "email3@email.com", "12345678")
        )

        every {
            service.findAllProducers()
        } returns producers

        mockMvc.perform(get("/producers"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray)
                .andExpect(content().json(objectMapper.writeValueAsString(producers)))
                .andExpect(jsonPath("$", hasSize<Any>(3)))
    }

    @Test
    fun `get producer by id should return 200`() {

        val uuid = UUID.randomUUID()

        val producer = Producer(
                id = uuid,
                name = "name",
                email = "email@email.com",
                document = "123456"
        )

        every {
            service.findProducerById(uuid)
        } returns producer

        mockMvc.perform(get("/producers/$uuid"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(producer)))
                .andExpect(jsonPath("$").exists())
    }

    @Test
    fun `get producer by id when id is nonexistent should return 404`() {

        val uuid = UUID.randomUUID()

        every {
            service.findProducerById(uuid)
        } throws ResourceNotFoundException("not found id $uuid")

        mockMvc.perform(get("/producers/{id}", uuid))
                .andExpect(status().isNotFound)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.message").value("not found id $uuid"))
    }

    @Test
    fun `post producer should return 201`() {

        val node = objectMapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("email", "email@email.com")
            put("document", "123456")
        }

        val producerForm = ProducerForm(node)

        val producer = Producer(
                id = UUID.randomUUID(),
                name = "name",
                email = "email@email.com",
                document = "123456"
        )

        every {
            service.saveProducer(producerForm)
        } returns producer

        mockMvc.perform(post("/producers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(node)))
                .andExpect(status().isCreated)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(producer)))
                .andExpect(header().string("location", containsString("/producers/${producer.id}")))
    }

    @Test
    fun `post producer when incorrect body should return 400`() {

        val node = objectMapper.createObjectNode()

        node.apply {
            put("email", "email@email.com")
            put("document", "123456")
        }

        mockMvc.perform(post("/producers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(node)))
                .andExpect(status().isBadRequest)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
    }

    @Test
    fun `post producer when service throws ResourceCreateException should return 400`() {

        val node = objectMapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("email", "email@email.com")
            put("document", "123456")
        }

        val producerForm = ProducerForm(node)

        every {
            service.saveProducer(producerForm)
        } throws ResourceCreateException("Creating error")

        mockMvc.perform(post("/producers")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(node)))
                .andExpect(status().isBadRequest)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
    }

    @Test
    fun `put producer should return 200`() {

        val uuid = UUID.randomUUID()

        val node = objectMapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("email", "email@email.com")
            put("document", "123456")
        }

        val producerForm = ProducerForm(node)

        val producer = Producer(
                id = uuid,
                name = "name",
                email = "email@email.com",
                document = "123456"
        )

        every {
            service.updateProducer(producerForm, uuid)
        } returns producer

        mockMvc.perform(put("/producers/{id}", uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(node)))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(producer)))
    }

    @Test
    fun `put producer when incorrect body should return 400`() {

        val uuid = UUID.randomUUID()

        val node = objectMapper.createObjectNode()

        node.apply {
            put("email", "email@email.com")
            put("document", "123456")
        }

        mockMvc.perform(put("/producers/$uuid")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(node)))
                .andExpect(status().isBadRequest)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
    }

    @Test
    fun `put producer when service throws ResourceUpdateException should return 400`() {

        val uuid = UUID.randomUUID()

        val node = objectMapper.createObjectNode()

        node.apply {
            put("name", "name")
            put("email", "email@email.com")
            put("document", "123456")
        }

        val producerForm = ProducerForm(node)

        every {
            service.updateProducer(producerForm, uuid)
        } throws ResourceUpdateException("Updating error")

        mockMvc.perform(put("/producers/{id}", uuid)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(node)))
                .andExpect(status().isBadRequest)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
    }

    @Test
    fun `delete event should return 204`() {

        val uuid = UUID.randomUUID()

        every {
            service.deleteProducer(uuid)
        } returns Unit

        mockMvc.perform(delete("/producers/$uuid"))
                .andExpect(status().isNoContent)
    }

    @Test
    fun `delete event when id is nonexistent should return 404`() {

        val uuid = UUID.randomUUID()

        every {
            service.deleteProducer(uuid)
        } throws ResourceNotFoundException("not found id $uuid")

        mockMvc.perform(delete("/producers/$uuid"))
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("$.message").value("not found id $uuid"))
    }
}