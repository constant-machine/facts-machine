package com.factsmachine.model

import java.util.concurrent.atomic.AtomicInteger

data class FactStatistics (
    val popularity: AtomicInteger
)