package com.category.ranking.rankingservice.storeDomain.adapter.infrastructure

import com.category.ranking.rankingservice.storeDomain.domain.QLikes
import com.category.ranking.rankingservice.storeDomain.domain.QReviews
import com.category.ranking.rankingservice.storeDomain.domain.QStore
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class StoreReviewsRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
): StoreReviewsRepository {
    private val store = QStore.store
    private val likes = QLikes.likes
    private val reviews = QReviews.reviews


}