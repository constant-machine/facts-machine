package com.factsmachine.service

import com.factsmachine.adapter.FactsAdapter
import com.factsmachine.adapter.UselessFactsAdapter
import com.fasterxml.jackson.databind.DeserializationFeature
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertEquals

class FactsServiceTest : KoinTest {

    val response = """{
  "id": "81061a01d37c6762c576e3ab68e8d055",
  "text": "This is an undisputable fact",
  "source": "djtech.net",
  "source_url": "http://www.djtech.net/humor/useless_facts.htm",
  "language": "en",
  "permalink": "https://uselessfacts.jsph.pl/api/v2/facts/81061a01d37c6762c576e3ab68e8d055"
}"""

    @get:Rule
    val setUpKoinContext = KoinTestRule.create {
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(response),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        modules(module {
            single<FactsService> { FactsServiceImpl(get(), get(), "") }
            single<StorageService> { InMemoryStorageService() }
            single<FactsAdapter> { UselessFactsAdapter(get(), "http://localhost:8080") }
            single {
                HttpClient(mockEngine) {
                    install(ContentNegotiation) {
                        jackson {
                            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        }
                    }
                }
            }
        })
    }

    @Test
    fun `Should fetch a fact and return it`() {
        runBlocking {
            val factsService: FactsService by inject()

            val fact = factsService.getNewFact()

            assertNotNull(fact)
            assertEquals("This is an undisputable fact", fact.fact)
        }
    }

    @Test
    fun `Should return a fact that was stored previously`() {
        runBlocking {
            val factsService: FactsService by inject()

            val fact = factsService.getNewFact()
            val storedFact = factsService.getFactById(fact.link)

            assertNotNull(storedFact)
            assertEquals("This is an undisputable fact", storedFact.fact)
        }
    }
}