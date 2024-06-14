package br.com.codigoalvo.kotlinmsg

import br.com.codigoalvo.kotlinmsg.model.Message
import br.com.codigoalvo.kotlinmsg.service.MessageService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import kotlin.test.Test

@WebMvcTest
class KotlinMsgApplicationMockTests(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var service: MessageService

    @Test
    fun contextLoads() {
        every { service.listAll() } returns listOf(
            Message("1", "First Message"),
            Message("2", "Second Message"))

        mockMvc.get("/v1/messages")
            .andExpect { status { isOk() }}
            .andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
            .andExpect {
                jsonPath("\$.[0].id") { value("1") }
                jsonPath("\$.[0].content") { value("First Message") }
                jsonPath("\$.[1].id") { value("2") }
                jsonPath("\$.[1].content") { value("Second Message") }
            }
    }

}