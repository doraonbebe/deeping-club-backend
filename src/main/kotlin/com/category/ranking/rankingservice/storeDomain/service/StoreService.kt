package com.category.ranking.rankingservice.storeDomain.service

import com.category.ranking.rankingservice.storeDomain.adapter.api.out.StoreResponse
import com.category.ranking.rankingservice.storeDomain.adapter.elasticsearch.ElasticSearchCustomRepository
import org.springframework.stereotype.Service


@Service
class StoreService(
    private val elasticSearchCustomRepo: ElasticSearchCustomRepository
) {

    fun searchStoresByLocation(lat: Double, lon: Double, radius: Int): List<StoreResponse> {
        return elasticSearchCustomRepo.findStoresByLocationAndDistance(lat, lon, radius)
    }
}