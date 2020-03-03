package com.example.eventproject.repository

import org.flywaydb.core.Flyway
import org.springframework.jdbc.core.JdbcTemplate
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils
import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.HikariConfig
import org.testcontainers.junit.jupiter.Container

class BasePostgresContainer {

    companion object {
        @Container
        private val postgresContainer = PostgreSQLContainer<Nothing>("postgres:latest").apply {
            withDatabaseName("eventprojecttest")
            start()
        }
    }

    private lateinit var dataSource: HikariDataSource

    private fun createRandomDatabase() {
        val databaseName = RandomStringUtils.randomAlphabetic(8).toLowerCase()

        postgresContainer.execInContainer("psql", "postgresql://${postgresContainer.username}:${postgresContainer.password}@localhost:5432/eventprojecttest",
                "-c", "CREATE DATABASE $databaseName;")

        postgresContainer.withDatabaseName(databaseName)

        doMigration()
    }

    private fun doMigration() {
        dataSource = buildDataSource()

        Flyway.configure().dataSource(dataSource).load().migrate()
    }

    private fun buildDataSource(): HikariDataSource {
        val config: HikariConfig = HikariConfig().apply {
            driverClassName = postgresContainer.driverClassName
            jdbcUrl = postgresContainer.jdbcUrl
            username = postgresContainer.username
            password = postgresContainer.password
        }

        return HikariDataSource(config)
    }

    fun jdbcTemplate(): JdbcTemplate {
        createRandomDatabase()

        return JdbcTemplate(dataSource)
    }

    fun clearPool() {
        dataSource.close()
    }
}