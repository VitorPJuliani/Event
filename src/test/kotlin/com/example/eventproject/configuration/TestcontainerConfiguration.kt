package com.example.eventproject.configuration

import com.example.eventproject.repository.BasePostgresContainer
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import javax.sql.DataSource

@TestConfiguration
class TestcontainerConfiguration {

//    @Bean
//    fun basePostgresContainer(): BasePostgresContainer {
//        return BasePostgresContainer()
//    }

    @Bean
    fun postgreSQLContainer(): PostgreSQLContainer<Nothing> {
        return PostgreSQLContainer<Nothing>("postgres:latest").apply {
            withDatabaseName("eventprojecttest")
            start()
        }
    }

    @Bean
    fun dataSource(postgreSQLContainer: PostgreSQLContainer<Nothing>): DataSource {
        return DataSourceBuilder.create()
                .url(postgreSQLContainer.jdbcUrl)
                .username(postgreSQLContainer.username)
                .password(postgreSQLContainer.password)
                .driverClassName(postgreSQLContainer.driverClassName)
                .build()
    }
}