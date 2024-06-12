package br.com.codigoalvo.kotlinmsg.controller

import br.com.codigoalvo.kotlinmsg.model.Message
import br.com.codigoalvo.kotlinmsg.service.MessageService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(val service: MessageService) {
    @GetMapping
    fun index(): List<Message> = service.findMessages()

    @PostMapping
    fun post(@RequestBody message: Message) {
        service.post(message)
    }
}