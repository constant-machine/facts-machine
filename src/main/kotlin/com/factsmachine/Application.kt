package com.factsmachine

import com.factsmachine.config.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDI()
    configureSecurity()
    configureMainRouting()
    configureStatisticsRouting()
    configureSerialization()
}
