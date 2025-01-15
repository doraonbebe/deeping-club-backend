package com.category.ranking.rankingservice.storeDomain.adapter.infrastructure

import com.category.ranking.rankingservice.storeDomain.adapter.api.out.database.ReviewResponse
import com.category.ranking.rankingservice.storeDomain.domain.QLikes
import com.category.ranking.rankingservice.storeDomain.domain.QReviewAttachments
import com.category.ranking.rankingservice.storeDomain.domain.QReviews
import com.category.ranking.rankingservice.storeDomain.domain.QStore
import com.category.ranking.rankingservice.userDomain.domain.QUser
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class StoreReviewsRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
): StoreReviewsRepository {
    private val store = QStore.store
    private val likes = QLikes.likes
    private val reviews = QReviews.reviews
    private val reviewAttachments = QReviewAttachments.reviewAttachments
    private val user = QUser.user




    override fun findReviewByStoreUuidAndUserId(uuid: String, userId: Long): List<ReviewResponse> {
        return queryFactory
            .select(Projections.constructor(
                ReviewResponse::class.java,
                store.latitude,
                store.longitude,
                user.nickName,
                reviews.id,
                reviews.content,
                reviews.writtenAt,
            ))
            .from(store)
            .join(store.reviews, reviews)
            .join(user).on(user.id.eq(reviews.userId))
            .where(
                store.uuid.eq(uuid)
                    .and(user.id.eq(userId))
            ).fetch()
    }

    override fun findReviewByIdAndStoreUuidAndUserId(id: Long, uuid: String, userId: Long): ReviewResponse? {
        return queryFactory
            .select(Projections.constructor(
                ReviewResponse::class.java,
                store.latitude,
                store.longitude,
                user.nickName,
                reviews.id,
                reviews.content,
                reviews.writtenAt,
            ))
            .from(store)
            .join(store.reviews, reviews)
            .join(user).on(user.id.eq(reviews.userId))
            .where(
                store.uuid.eq(uuid)
                    .and(reviews.id.eq(id))
                    .and(user.id.eq(userId))
            ).fetchOne()
    }


}