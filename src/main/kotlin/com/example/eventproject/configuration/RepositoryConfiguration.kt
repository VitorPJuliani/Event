package com.example.eventproject.configuration

import com.example.eventproject.repository.EventRepository
import com.example.eventproject.repository.JdbcEventRepository
import com.example.eventproject.repository.JdbcProducerRepository
import com.example.eventproject.repository.ProducerRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
class RepositoryConfiguration {

    @Bean
    fun eventRepository(jdbcTemplate: JdbcTemplate): EventRepository {
        return JdbcEventRepository(jdbcTemplate)
    }

    @Bean
    fun producerRepository(jdbcTemplate: JdbcTemplate): ProducerRepository {
        return JdbcProducerRepository(jdbcTemplate)
    }
}