package com.factsmachine.config

import com.factsmachine.service.error.FactNotFoundException
import com.factsmachine.service.error.NoFactIdInRequestException
import io.ktor.client.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureErrorHandling() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is FactNotFoundException -> {
                    call.respondText(text = "404: Fact not found", status = HttpStatusCode.NotFound)
                }
                is NoFactIdInRequestException -> {
                    call.respondText(text = cause.message.orEmpty(), status = HttpStatusCode.BadRequest)
                }
                // below are non-business logic exceptions
                is ClientRequestException -> {
                    if (HttpStatusCode.RequestTimeout == cause.response.status) {
                        call.respondText(text = HttpStatusCode.RequestTimeout.description, status = HttpStatusCode.RequestTimeout)
                    } else {
                        call.respondText(text = HttpStatusCode.BadRequest.description, status = HttpStatusCode.BadRequest)
                    }
                }
                is ServerResponseException -> {
                    when (cause.response.status) {
                        HttpStatusCode.BadGateway, HttpStatusCode.TooManyRequests, HttpStatusCode.GatewayTimeout -> {
                            call.respondText(text = cause.response.status.description, status = cause.response.status)
                        }
                        else -> {
                            call.respondText(text = HttpStatusCode.BadGateway.description, status = HttpStatusCode.BadGateway)
                        }
                    }
                }
                else -> {
                    call.respondText(text = HttpStatusCode.InternalServerError.description, status = HttpStatusCode.InternalServerError)
                }
            }
        }
    }
}