package br.com.codigoalvo.kotlinmsg

import br.com.codigoalvo.kotlinmsg.model.Message
import mu.KotlinLogging
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [
        "spring.datasource.url=jdbc:h2:mem:testdb"
    ]
)
class KotlinMsgApplicationTests(@Autowired val client: TestRestTemplate) {
    private val logger = KotlinLogging.logger {}

    @Test
    fun testPostAndGet() {
        val postRequest = "Test123"
        val postResponse = client.postForEntity("/v1/messages", Message(null, postRequest), Map::class.java)
        logger.info("postResponse: $postResponse")
        Assertions.assertThat(postResponse.statusCode).isEqualTo(HttpStatus.CREATED)
        val postResponseId = postResponse.body?.get("id")

        val getResponse: ResponseEntity<Map<String, String>> = client.getForEntity<Map<String, String>>("/v1/messages/${postResponseId}")
        logger.info("getResponse: $getResponse")
        Assertions.assertThat(getResponse.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(getResponse.body).isNotEmpty
        Assertions.assertThat(getResponse.body?.get("id")).isNotNull()
        Assertions.assertThat(getResponse.body?.get("id")).isEqualTo(postResponseId)
    }

}
