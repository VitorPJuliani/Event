package com.example.eventproject.repository

import com.example.eventproject.model.Producer
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.*

@Repository
class JdbcProducerRepository(private val jdbcTemplate: JdbcTemplate) : ProducerRepository {

    override fun findAllProducers(): List<Producer> {
        return jdbcTemplate.query("SELECT * FROM PRODUCER") {
            rs: ResultSet, _: Int -> Producer(rs.getObject("id", UUID::class.java), rs.getString("name"), rs.getString("email"), rs.getString("document"))
        }
    }
}