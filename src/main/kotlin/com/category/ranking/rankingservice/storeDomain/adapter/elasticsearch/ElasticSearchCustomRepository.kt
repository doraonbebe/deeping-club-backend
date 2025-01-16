package com.category.ranking.rankingservice.storeDomain.adapter.elasticsearch

import com.category.ranking.rankingservice.storeDomain.adapter.api.out.elasticsearch.StoreResponse

interface ElasticSearchCustomRepository {

    fun findStoresByRadiusAndCategory(lat: Double, lon: Double, radius: Int, category: String?, filter: String?): List<StoreResponse>

    fun findStoresByRadiusLimit(lat: Double, lon: Double, radius: Int, limit: Int): List<StoreResponse>
}