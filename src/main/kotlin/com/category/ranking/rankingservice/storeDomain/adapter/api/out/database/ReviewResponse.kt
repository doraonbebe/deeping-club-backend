package com.category.ranking.rankingservice.storeDomain.adapter.api.out.database

import java.time.LocalDate

data class ReviewResponse(
    val latitude: Double,
    val longitude: Double,
    val userNickName: String,
    val id: Long,
    val content: String,
    val writtenAt: LocalDate

)
