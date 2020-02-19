package com.example.eventproject.repository

import com.example.eventproject.form.EventForm
import com.example.eventproject.model.Event
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDate
import java.util.UUID

class JdbcEventRepository(private val jdbcTemplate: JdbcTemplate) : EventRepository {

    companion object {
        private const val selectEventByIdQuery = "SELECT id, name, description, date, producer FROM event WHERE id = :id"
        private const val selectAllEventsQuery = "SELECT id, name, description, date, producer FROM event"
        private const val insertEventQuery = "INSERT INTO event (name, description, date, producer) VALUES (?,?,?,?) RETURNING id, name, description, date, producer"
        private const val updateEventQuery = "UPDATE event SET name = ?, description = ?, date = ?, producer = ? WHERE id = ? RETURNING id, name, description, date, producer"
        private const val deleteEventQuery = "DELETE FROM event WHERE id = :id"
    }

    override fun findEventById(id: UUID): Event? {

        val parameter = MapSqlParameterSource()
                .addValue("id", id)

        val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(jdbcTemplate)

        return try {
            namedParameterJdbcTemplate.queryForObject(selectEventByIdQuery, parameter) { rs: ResultSet, _: Int ->
                Event(rs.getObject("id", UUID::class.java),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getObject("date", LocalDate::class.java),
                        rs.getObject("producer", UUID::class.java))
            }
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    override fun findAllEvents(): List<Event> {
        return jdbcTemplate.query(selectAllEventsQuery) { rs: ResultSet, _: Int ->
            Event(rs.getObject("id", UUID::class.java),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getObject("date", LocalDate::class.java),
                    rs.getObject("producer", UUID::class.java))
        }
    }

    override fun saveEvent(event: EventForm): Event? {
        return jdbcTemplate.execute { connection: Connection ->
            val preparedStatement = connection.prepareStatement(insertEventQuery)
            preparedStatement.setString(1, event.name)
            preparedStatement.setString(2, event.description)
            preparedStatement.setObject(3, event.date)
            preparedStatement.setObject(4, event.producer)

            try {
                val rs = preparedStatement.executeQuery()

                rs.next()

                Event(rs.getObject("id", UUID::class.java),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getObject("date", LocalDate::class.java),
                        rs.getObject("producer", UUID::class.java))
            } catch (e: SQLException) {
                null
            }
        }
    }

    override fun updateEvent(event: EventForm, id: UUID): Event? {
        return jdbcTemplate.execute { connection: Connection ->
            val preparedStatement = connection.prepareStatement(updateEventQuery)
            preparedStatement.setString(1, event.name)
            preparedStatement.setString(2, event.description)
            preparedStatement.setObject(3, event.date)
            preparedStatement.setObject(4, event.producer)
            preparedStatement.setObject(5, id)

            try {
                val rs = preparedStatement.executeQuery()

                if (rs.next())
                    Event(rs.getObject("id", UUID::class.java),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getObject("date", LocalDate::class.java),
                            rs.getObject("producer", UUID::class.java))
                else
                    null
            } catch (e: SQLException) {
                null
            }
        }
    }

    override fun deleteEvent(id: UUID): Int {
        val parameter = MapSqlParameterSource()
                .addValue("id", id)

        val namedParameterJdbcTemplate = NamedParameterJdbcTemplate(jdbcTemplate)

        return namedParameterJdbcTemplate.update(deleteEventQuery, parameter)
    }
}