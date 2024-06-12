package br.com.codigoalvo.kotlinmsg.service

import br.com.codigoalvo.kotlinmsg.repository.MessageRepository
import br.com.codigoalvo.kotlinmsg.model.Message
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.NotFound

@Service
class MessageService(val repository: MessageRepository) {

    fun listAll(): List<Message> = repository.findMessages()

    fun getById(id: String): Message? = repository.findById(id).orElseThrow { NotFoundException() }

    fun post(message: Message): String? = repository.save(message).id

    fun put(message: Message): Message? = repository.save(message)

    fun deleteById(id: String): Unit? = repository.deleteById(id)

}