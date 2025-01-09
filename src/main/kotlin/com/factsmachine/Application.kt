package com.factsmachine

import com.factsmachine.config.configureDI
import com.factsmachine.config.configureRouting
import com.factsmachine.config.configureSecurity
import com.factsmachine.config.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDI()
    configureSecurity()
    configureRouting()
    configureSerialization()
}
