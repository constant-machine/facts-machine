package com.factsmachine.config

import com.factsmachine.service.StatisticsService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureStatisticsRouting() {

    val statisticsService: StatisticsService by inject()

    routing {
        authenticate("admin") {
            get("/admin/statistics") {
                call.respond(statisticsService.getStatistics())
            }
        }
    }
}