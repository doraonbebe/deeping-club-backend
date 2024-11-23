package com.category.ranking.rankingservice.storeDomain.service

import com.category.ranking.rankingservice.storeDomain.adapter.api.`in`.ReviewCreateRequest
import com.category.ranking.rankingservice.storeDomain.adapter.api.`in`.ReviewUpdateRequest
import com.category.ranking.rankingservice.storeDomain.domain.Reviews
import com.category.ranking.rankingservice.storeDomain.domain.Status
import com.category.ranking.rankingservice.storeDomain.repository.ReviewsJPARepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StoreReviewsService(
    val reviewJPARepo: ReviewsJPARepository,
    val storeService: StoreService
) {

    @Transactional
    fun saveStoreReview(uuid: String, reviewCreateRequest: ReviewCreateRequest) {
        val userId = reviewCreateRequest.userId
        val rating = reviewCreateRequest.rating
        val content = reviewCreateRequest.content

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

    @Transactional
    fun updateStoreReview(uuid: String, id: Long, reviewUpdateRequest: ReviewUpdateRequest) {
        val rating = reviewUpdateRequest.rating
        val content = reviewUpdateRequest.content

        val review = findReviewByIdOrNull(id)

        review.updateReview(review.status, content, rating)


    }

    @Transactional
    fun softDeleteStoreReview(uuid: String, id: Long) {
        val review = findReviewByIdOrNull(id)
        review.updateReview(Status.DELETION, review.content, review.rating)
    }

    @Transactional(readOnly = true)
    fun findReviewByIdOrNull(id: Long): Reviews {
        return reviewJPARepo.findByIdOrNull(id)?: throw EntityNotFoundException("review $id not found")
    }

}