package com.factsmachine.service

import java.util.*
import java.util.concurrent.atomic.AtomicLong

interface IdGeneratorService {
    suspend fun getNewId(): String
}

class Base62IdGeneratorService: IdGeneratorService {

    private val atomicCounter = AtomicLong()

    override suspend fun getNewId(): String {
        val newId = atomicCounter.incrementAndGet()
        return Base64.getUrlEncoder().encodeToString(newId.toString().toByteArray(Charsets.UTF_8))
    }
}