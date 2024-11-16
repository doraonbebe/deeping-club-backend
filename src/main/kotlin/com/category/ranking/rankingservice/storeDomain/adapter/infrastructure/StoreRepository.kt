package com.category.ranking.rankingservice.storeDomain.adapter.infrastructure

import com.category.ranking.rankingservice.storeDomain.adapter.api.out.database.StoreResponse

interface StoreRepository {

    fun findTopStoresByLikeCnt(uuids: List<String>, limit: Int): List<StoreResponse>
}