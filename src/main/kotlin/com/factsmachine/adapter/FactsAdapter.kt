package com.factsmachine.adapter

import com.factsmachine.adapter.dto.FactDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface FactsAdapter {
    suspend fun getFact(): FactDto
}

class UselessFactsAdapter(private val apiClient: HttpClient, private val url: String): FactsAdapter {

    override suspend fun getFact(): FactDto {
        val httpResponse =  apiClient.get(url) {
            parameter("language", "en")
        }
        return httpResponse.body<FactDto>()
    }
}