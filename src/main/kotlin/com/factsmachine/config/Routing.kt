package com.factsmachine.config

import com.factsmachine.service.FactsService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val factsService: FactsService by inject()

    routing {
        post("/facts") {
            call.respond(factsService.getNewFact())
        }
        get("/facts/{id}") {
            val id = call.parameters["id"]
            call.respond(factsService.getFactById(id))
        }
        get("/facts") {
            call.respond(factsService.getFacts())
        }
        get("/facts/{id}/redirect") {
            val id = call.parameters["id"]
            val fact = factsService.getFactById(id)
            call.respondRedirect(fact.originalPermalink)
        }
    }
}
