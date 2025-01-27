package com.factsmachine.config

import com.factsmachine.adapter.FactsAdapter
import com.factsmachine.adapter.UselessFactsAdapter
import com.factsmachine.service.*
import com.fasterxml.jackson.databind.DeserializationFeature
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.client.plugins.contentnegotiation.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureDI() {
    val uselessFactsUrl = environment.config.property("ktor.uselessfactsservice.url").getString()
    val baseUrl = environment.config.property("ktor.baseurl").getString()

    install(Koin) {
        slf4jLogger()
        modules(module {
            single<FactsService> { FactsServiceImpl(get(), get(), get(), baseUrl) }
            single<StorageService> { InMemoryStorageService() }
            single<FactsAdapter> { UselessFactsAdapter(get(), uselessFactsUrl) }
            single<StatisticsService> { StatisticsServiceImpl(get(), baseUrl) }
            single<IdGeneratorService> { Base62IdGeneratorService() }
            single {
                HttpClient(OkHttp) {
                    install(ContentNegotiation) {
                        jackson {
                            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        }
                    }
                }
            }
        })
    }
}
