package com.factsmachine

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import org.koin.core.context.stopKoin
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        environment {
            val appConfig = ApplicationConfig("application.conf")
            val testConfig = MapApplicationConfig().apply {
                put("ktor.security.username", "admin")
                put("ktor.security.password", "hashed_password")
            }
            config = appConfig.mergeWith(testConfig)
        }

        client.get("/facts").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @AfterTest
    fun cleanUp() {
        stopKoin()
    }
}
