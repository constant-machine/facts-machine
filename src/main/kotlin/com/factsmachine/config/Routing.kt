package com.factsmachine.config

import com.factsmachine.service.FactsService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val factsService: FactsService by inject()

    routing {
        get("/") {
            call.respond(factsService.getNewFact())
        }
    }
}
