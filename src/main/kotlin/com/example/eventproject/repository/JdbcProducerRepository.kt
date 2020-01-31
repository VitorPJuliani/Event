package com.example.eventproject.repository

import com.example.eventproject.dto.ProducerDto
import com.example.eventproject.model.Producer
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

@Repository
class JdbcProducerRepository(private val jdbcTemplate: JdbcTemplate) : ProducerRepository {

    override fun findProducer(id: UUID): Producer? {

        val selectOneQuery: String = "SELECT id, name, email, document FROM producer WHERE id = :id"

        val parameter = MapSqlParameterSource()
                .addValue("id", id)

        val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(jdbcTemplate)

        return namedParameterJdbcTemplate.queryForObject(selectOneQuery, parameter) { rs: ResultSet, _: Int ->
            Producer(rs.getObject("id", UUID::class.java), rs.getString("name"), rs.getString("email"), rs.getString("document"))
        }
    }

    override fun findAllProducers(): List<Producer> {

        val selectAllQuery = "SELECT id, name, email, document FROM producer"

        return jdbcTemplate.query(selectAllQuery) { rs: ResultSet, _: Int ->
            Producer(rs.getObject("id", UUID::class.java), rs.getString("name"), rs.getString("email"), rs.getString("document"))
        }
    }

    override fun saveProducer(producer: ProducerDto): Producer? {

        val insertQuery = "INSERT INTO producer(name, email, document) VALUES (?, ?, ?) RETURNING id, name, email, document"

        return jdbcTemplate.execute { connection: Connection ->

            val preparedStatement: PreparedStatement = connection.prepareStatement(insertQuery)
            preparedStatement.setString(1, producer.name)
            preparedStatement.setString(2, producer.email)
            preparedStatement.setString(3, producer.document)

            val rs = preparedStatement.executeQuery()
            if (rs.next())
                Producer(rs.getObject("id", UUID::class.java), rs.getString("name"), rs.getString("email"), rs.getString("document"))
            else
                null

        }
    }

    override fun updateProducer(producer: ProducerDto, id: UUID): Producer? {

        val updateQuery = "UPDATE producer SET name = ?, email = ?, document = ? WHERE id = ? RETURNING id, name, email, document"

        return jdbcTemplate.execute { connection: Connection ->

            val preparedStatement: PreparedStatement = connection.prepareStatement(updateQuery)
            preparedStatement.setString(1, producer.name)
            preparedStatement.setString(2, producer.email)
            preparedStatement.setString(3, producer.document)
            preparedStatement.setObject(4, id)

            val rs = preparedStatement.executeQuery()
            if (rs.next())
                Producer(rs.getObject("id", UUID::class.java), rs.getString("name"), rs.getString("email"), rs.getString("document"))
            else
                null
        }
    }

    override fun destroyProducer(id: UUID): Int {

        val deleteQuery = "DELETE FROM producer WHERE id = :id"

        val parameter = MapSqlParameterSource()
                .addValue("id", id)

        val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(jdbcTemplate)

        return namedParameterJdbcTemplate.update(deleteQuery, parameter)
    }
}