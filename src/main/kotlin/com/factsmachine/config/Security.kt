package com.factsmachine.config

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureSecurity() {

    val adminUsername = environment.config.property("ktor.security.username").getString()
    val adminPassword = environment.config.property("ktor.security.password").getString()

    authentication {
        basic(name = "admin") {
            realm = "Ktor Server"
            validate { credentials ->
                if (adminUsername == credentials.name && adminPassword == credentials.password) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
}
