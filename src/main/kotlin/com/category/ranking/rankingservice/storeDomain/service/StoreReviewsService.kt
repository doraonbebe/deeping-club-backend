package com.category.ranking.rankingservice.storeDomain.service

import com.category.ranking.rankingservice.storeDomain.adapter.api.`in`.StoreReviewRequest
import com.category.ranking.rankingservice.storeDomain.domain.Reviews
import com.category.ranking.rankingservice.storeDomain.domain.Status
import com.category.ranking.rankingservice.storeDomain.repository.ReviewsJPARepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class StoreReviewsService(
    val reviewJPARepo: ReviewsJPARepository,
    val storeService: StoreService
) {

    fun saveStoreReview(uuid: String, reviewRequest: StoreReviewRequest) {
        val userId = reviewRequest.userId
        val rating = reviewRequest.rating
        val content = reviewRequest.content

        val store = storeService.findStoreByUuid(uuid) ?: throw EntityNotFoundException("Store $uuid not found")

        reviewJPARepo.save(
            Reviews.createReview(
                store,
                userId,
                Status.EXPOSURE,
                content,
                rating
            )
        )
    }

}