package com.category.ranking.rankingservice.storeDomain.adapter.infrastructure

import com.category.ranking.rankingservice.storeDomain.adapter.api.out.database.StoreResponse
import com.category.ranking.rankingservice.storeDomain.domain.QCategory
import com.category.ranking.rankingservice.storeDomain.domain.QLikes
import com.category.ranking.rankingservice.storeDomain.domain.QStore
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class StoreRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
): StoreRepository {
    private val store = QStore.store
    private val likes = QLikes.likes
    private val category = QCategory.category1

    override fun findTopStoresByLikeCnt(uuids: List<String>, limit: Int): List<StoreResponse> {
        return queryFactory
            .select(Projections.constructor(
                StoreResponse::class.java,
                store.name,
                category.category,
                store.latitude,
                store.longitude,
                store.address,
                store.uuid
            ))
            .from(category)
            .join(category.stores, store)
            .join(store.likes, likes)
            .where(store.uuid.notIn(uuids))
            .groupBy(store.id)
            .orderBy(store.id.count().desc())
            .limit(limit.toLong())
            .fetch()
    }
}