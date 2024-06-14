package br.com.codigoalvo.kotlinmsg.service

import br.com.codigoalvo.kotlinmsg.Either
import br.com.codigoalvo.kotlinmsg.repository.MessageRepository
import br.com.codigoalvo.kotlinmsg.model.Message
import mu.KotlinLogging
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class MessageService(val repository: MessageRepository) {

    private val logger = KotlinLogging.logger {}

    fun listAll(): List<Message> = repository.findMessages().also { logger.info("Listing all the messages") }

    fun getById(id: String): Either <String, Message>? {
        logger.info("Searching for message with id: $id")
        return runCatching {
            repository.findById(id).orElseThrow { NotFoundException() }
        }.onFailure {
            logger.error { "Failed to lookup message $id, ${it.message}" }
        }.fold({
            Either.right(it)
        }, {
            Either.left("Failed to lookup message $id, ${it.message}")
        })
    }

    fun post(message: Message): Message = repository.save(message).also { logger.info("Saving message: $message")}

    fun put(message: Message): Message? = repository.save(message).also { logger.info("Updating message: $message")}

    fun deleteById(id: String): Unit? = repository.deleteById(id).also { logger.info("Deleting message with id: $id") }

}