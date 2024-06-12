package br.com.codigoalvo.kotlinmsg.repository

import br.com.codigoalvo.kotlinmsg.model.Message
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface MessageRepository : CrudRepository<Message, String> {

    @Query("SELECT * FROM MESSAGES")
    fun findMessages(): List<Message>
}