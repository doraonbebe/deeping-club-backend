package com.category.ranking.rankingservice.storeDomain.adapter.api.`in`

data class StoreReviewRequest(
    val userId: Long,
    val content: String,
    val rating: Double
)
