package com.factsmachine.adapter.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class FactDto(
    val id: String,
    val text: String,
    val source: String,
    @JsonProperty("source_url")
    val sourceUrl: String,
    val language: String,
    val permalink: String
)