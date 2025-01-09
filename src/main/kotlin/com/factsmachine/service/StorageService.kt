package com.factsmachine.service

import com.factsmachine.model.FactHolder
import io.ktor.util.collections.*

interface StorageService {
    fun saveFact(id: String, factHolder: FactHolder)
    fun getFact(id: String): FactHolder?
}

class InMemoryStorageService : StorageService {

    private val concurrentMap: ConcurrentMap<String, FactHolder> = ConcurrentMap()

    override fun saveFact(id: String, factHolder: FactHolder) {
        concurrentMap[id] = factHolder
    }

    override fun getFact(id: String): FactHolder? {
        return concurrentMap[id]
    }
}