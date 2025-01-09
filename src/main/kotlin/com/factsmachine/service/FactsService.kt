package com.factsmachine.service

import com.factsmachine.adapter.FactsAdapter
import com.factsmachine.model.Fact
import com.factsmachine.model.FactHolder
import com.factsmachine.service.dto.FactResponseDto
import java.util.concurrent.atomic.AtomicInteger

interface FactsService {
    suspend fun getNewFact(): FactResponseDto
    suspend fun getFactById(factId: String)
}

class FactsServiceImpl(
    private val factsAdapter: FactsAdapter,
    private val storageService: StorageService,
    private val baseUrl: String) : FactsService {

    override suspend fun getNewFact(): FactResponseDto {
        val fact = factsAdapter.getFact()
        storageService.saveFact(fact.id, FactHolder(Fact(fact.id, fact.text), AtomicInteger(0)))
        return FactResponseDto(fact.text, baseUrl + fact.id)
    }

    override suspend fun getFactById(factId: String) {

    }
}
