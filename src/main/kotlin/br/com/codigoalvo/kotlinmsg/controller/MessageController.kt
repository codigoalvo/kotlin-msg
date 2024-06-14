package br.com.codigoalvo.kotlinmsg.controller

import br.com.codigoalvo.kotlinmsg.model.Message
import br.com.codigoalvo.kotlinmsg.service.MessageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/v1/messages")
class MessageController(val service: MessageService) {

    @GetMapping
    fun listAll(): ResponseEntity<*>? =
        ResponseEntity.ok(service.listAll())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<*>? =
        ResponseEntity.ok(service.getById(id))

    @PostMapping
    fun post(@RequestBody message: Message): ResponseEntity<*> {
        val savedMessage = service.post(message)
        val uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedMessage.id).toUri()
        return ResponseEntity.created(uri).body(savedMessage)
    }

    @PutMapping("/{id}")
    fun put(@PathVariable id: String, @RequestBody message: Message): ResponseEntity<*> {
        return ResponseEntity.ok(service.put(Message(id, message.content)))
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String): ResponseEntity<*>? =
        ResponseEntity.ok(service.deleteById(id))


}