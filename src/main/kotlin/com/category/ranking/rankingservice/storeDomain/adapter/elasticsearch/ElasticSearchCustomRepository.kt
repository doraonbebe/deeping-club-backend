package com.category.ranking.rankingservice.storeDomain.adapter.elasticsearch

import com.category.ranking.rankingservice.storeDomain.adapter.api.out.StoreResponse

interface ElasticSearchCustomRepository {

    fun findStoresByLocationAndDistance(lat: Double, lon: Double, radius: Int): List<StoreResponse>
}