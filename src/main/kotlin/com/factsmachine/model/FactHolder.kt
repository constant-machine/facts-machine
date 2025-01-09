package com.factsmachine.model

import java.util.concurrent.atomic.AtomicInteger

data class FactHolder (
    val fact: Fact,
    val popularity: AtomicInteger
)
