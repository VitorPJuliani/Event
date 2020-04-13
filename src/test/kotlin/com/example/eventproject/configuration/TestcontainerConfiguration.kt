package com.example.eventproject.configuration

import com.example.eventproject.repository.BasePostgresContainer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
@TestConfiguration
class TestcontainerConfiguration {

    @Bean
    fun basePostgresContainer(): BasePostgresContainer {
        return BasePostgresContainer()
    }
}