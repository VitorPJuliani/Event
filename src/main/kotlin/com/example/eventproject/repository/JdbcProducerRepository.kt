package com.example.eventproject.repository

import com.example.eventproject.form.ProducerForm
import com.example.eventproject.model.Producer
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.UUID

class JdbcProducerRepository(private val jdbcTemplate: JdbcTemplate) : ProducerRepository {

    companion object {
        const val selectProducerByIdQuery = "SELECT id, name, email, document FROM producer WHERE id = :id"
        const val selectAllProducersQuery = "SELECT id, name, email, document FROM producer"
        const val insertProducerQuery = "INSERT INTO producer(name, email, document) VALUES (?, ?, ?) RETURNING id, name, email, document"
        const val updateProducerQuery = "UPDATE producer SET name = ?, email = ?, document = ? WHERE id = ? RETURNING id, name, email, document"
        const val deleteProducerQuery = "DELETE FROM producer WHERE id = :id"
    }

    override fun findProducerById(id: UUID): Producer? {
        val parameter = MapSqlParameterSource()
                .addValue("id", id)

        val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(jdbcTemplate)

        return try {
            namedParameterJdbcTemplate.queryForObject(selectProducerByIdQuery, parameter) { rs: ResultSet, _: Int ->
                Producer(rs.getObject("id", UUID::class.java), rs.getString("name"), rs.getString("email"), rs.getString("document"))
            }
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    override fun findAllProducers(): List<Producer> {
        return jdbcTemplate.query(selectAllProducersQuery) { rs: ResultSet, _: Int ->
            Producer(rs.getObject("id", UUID::class.java), rs.getString("name"), rs.getString("email"), rs.getString("document"))
        }
    }

    override fun saveProducer(producer: ProducerForm): Producer? {
        return jdbcTemplate.execute { connection: Connection ->

            val preparedStatement: PreparedStatement = connection.prepareStatement(insertProducerQuery)
            preparedStatement.setString(1, producer.name)
            preparedStatement.setString(2, producer.email)
            preparedStatement.setString(3, producer.document)

            try {
                val rs = preparedStatement.executeQuery()

                rs.next()

                Producer(rs.getObject("id", UUID::class.java), rs.getString("name"), rs.getString("email"), rs.getString("document"))
            } catch (e: SQLException) {
                null
            }

        }
    }

    override fun updateProducer(producer: ProducerForm, id: UUID): Producer? {
        return jdbcTemplate.execute { connection: Connection ->

            val preparedStatement: PreparedStatement = connection.prepareStatement(updateProducerQuery)
            preparedStatement.setString(1, producer.name)
            preparedStatement.setString(2, producer.email)
            preparedStatement.setString(3, producer.document)
            preparedStatement.setObject(4, id)

            try {
                val rs = preparedStatement.executeQuery()
                if (rs.next())
                    Producer(rs.getObject("id", UUID::class.java), rs.getString("name"), rs.getString("email"), rs.getString("document"))
                else
                    null
            } catch (e: SQLException) {
                null
            }
        }
    }

    override fun deleteProducer(id: UUID): Int {
        val parameter = MapSqlParameterSource()
                .addValue("id", id)

        val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(jdbcTemplate)

        return namedParameterJdbcTemplate.update(deleteProducerQuery, parameter)
    }
}