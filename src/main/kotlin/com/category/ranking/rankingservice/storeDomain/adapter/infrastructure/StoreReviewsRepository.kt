package com.category.ranking.rankingservice.storeDomain.adapter.infrastructure

import com.category.ranking.rankingservice.storeDomain.adapter.api.out.database.ReviewResponse

interface StoreReviewsRepository {

    fun findReviewByStoreUuidAndUserId(uuid: String, userId: Long): List<ReviewResponse>
    fun findReviewByIdAndStoreUuidAndUserId(id:Long, uuid: String, userId: Long): ReviewResponse?

}