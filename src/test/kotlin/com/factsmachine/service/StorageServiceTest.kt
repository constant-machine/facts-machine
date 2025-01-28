package com.factsmachine.service

import com.factsmachine.model.Fact
import com.factsmachine.model.FactHolder
import com.factsmachine.model.FactStatistics
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.Test

class StorageServiceTest {

    @Test
    fun `Should store a fact successfully`() {
        val id = "factId"
        val storageService = InMemoryStorageService()

        assertDoesNotThrow {
            storageService.saveFact(
                id,
                FactHolder(
                    Fact(id, "", "It is what it is", ""),
                    FactStatistics(AtomicInteger(0))
                )
            )
        }
    }

    @Test
    fun `Should store and retrieve a fact`() {
        val id = "factId"
        val storageService = InMemoryStorageService()
        val fact = Fact(id, "", "It is what it is", "")

        storageService.saveFact(
            id,
            FactHolder(
                fact,
                FactStatistics(AtomicInteger(0))
            )
        )

        val savedFact = storageService.getFact(id)?.fact
        assertEquals(fact, savedFact)
    }

    @Test
    fun `Should retrieve all facts`() {
        val id = "factId"
        val storageService = InMemoryStorageService()
        val fact = Fact(id, "", "It is what it is", "")

        storageService.saveFact(
            id,
            FactHolder(
                fact,
                FactStatistics(AtomicInteger(0))
            )
        )

        val savedFacts = storageService.getAllFacts()
        assertEquals(1, savedFacts.size)
        assertEquals(fact, savedFacts.first().fact)
    }
}