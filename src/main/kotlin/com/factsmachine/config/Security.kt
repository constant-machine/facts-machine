package com.factsmachine.config

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureSecurity() {

    val adminUsername = environment.config.property("ktor.security.username").getString()
    val adminHashedPassword = environment.config.property("ktor.security.password").getString()

    // Todo:(Far from the product ready, implemented just to have some in place)
    authentication {
        basic(name = "admin") {
            realm = "Ktor Server"
            validate { credentials ->
                if (adminUsername == credentials.name &&
                    BCrypt.verifyer().verify(credentials.password.toCharArray(), adminHashedPassword).verified) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
}
