package br.com.codigoalvo.kotlinmsg.service

import br.com.codigoalvo.kotlinmsg.repository.MessageRepository
import br.com.codigoalvo.kotlinmsg.model.Message
import org.springframework.stereotype.Service

@Service
class MessageService(val repository: MessageRepository) {

    fun findMessages() :List<Message> = repository.findMessages()

    fun post(message: Message) {
        repository.save(message)
    }

}