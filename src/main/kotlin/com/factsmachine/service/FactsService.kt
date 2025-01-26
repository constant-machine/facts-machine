package com.factsmachine.service

import com.factsmachine.adapter.FactsAdapter
import com.factsmachine.model.Fact
import com.factsmachine.model.FactHolder
import com.factsmachine.model.FactStatistics
import com.factsmachine.service.dto.FactResponseDto
import com.factsmachine.service.dto.NewFactResponse
import com.factsmachine.service.error.FactNotFoundException
import com.factsmachine.service.error.NoFactIdInRequestException
import java.util.concurrent.atomic.AtomicInteger
import kotlin.streams.toList

interface FactsService {
    suspend fun getNewFact(): NewFactResponse
    suspend fun getFactById(factId: String?): FactResponseDto
    suspend fun getFacts(): List<FactResponseDto>
}

class FactsServiceImpl(
    private val factsAdapter: FactsAdapter,
    private val storageService: StorageService,
    private val baseUrl: String) : FactsService {

    override suspend fun getNewFact(): NewFactResponse {
        val fact = factsAdapter.getFact()
        val statistics = FactStatistics(AtomicInteger(0))
        val factHolder = FactHolder(Fact(fact.id, fact.text, fact.permalink), statistics)
        storageService.saveFact(fact.id, factHolder)
        return NewFactResponse(fact.text, baseUrl + fact.id)
    }

    override suspend fun getFactById(factId: String?): FactResponseDto {
        if (factId == null) throw NoFactIdInRequestException()
        val factHolder = storageService.getFact(factId) ?: throw FactNotFoundException()
        factHolder.statistics.popularity.incrementAndGet()
        return FactResponseDto(factHolder.fact.text, factHolder.fact.originalPermalink)
    }

    override suspend fun getFacts(): List<FactResponseDto> {
        val factHolders = storageService.getAllFacts()
        return factHolders.stream()
            .map { FactResponseDto(it.fact.text, it.fact.originalPermalink) }
            .toList()
    }
}
