package com.factsmachine

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.ktor.util.reflect.*
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    val mockedResponse = """{
  "id": "81061a01d37c6762c576e3ab68e8d055",
  "text": "This is an undisputable fact",
  "source": "djtech.net",
  "source_url": "http://www.djtech.net/humor/useless_facts.htm",
  "language": "en",
  "permalink": "https://uselessfacts.jsph.pl/api/v2/facts/81061a01d37c6762c576e3ab68e8d055"
}"""

    @Ignore
    @Test
    fun testRoot() = testApplication {
        environment {
            config = ApplicationConfig("application.conf")
        }

        externalServices {
            hosts("https://uselessfacts.jsph.pl") {
                routing {
                    get("/api/v2/facts/random") {
                        call.respond(mockedResponse, typeInfo<String>())
                    }
                }
            }
        }

        client.post("/facts").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

}
