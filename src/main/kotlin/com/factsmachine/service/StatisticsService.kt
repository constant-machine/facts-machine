package com.factsmachine.service

import com.factsmachine.service.dto.StatisticsResponseDto
import kotlin.streams.toList

interface StatisticsService {
    suspend fun getStatistics(): List<StatisticsResponseDto>
}

class StatisticsServiceImpl(
    private val storageService: StorageService,
    private val baseUrl: String
) : StatisticsService {

    override suspend fun getStatistics(): List<StatisticsResponseDto> {
        return storageService.getAllFacts().stream()
            .map { StatisticsResponseDto(baseUrl + it.fact.id, it.statistics.popularity.get()) }
            .toList()
    }
}