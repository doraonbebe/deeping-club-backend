package com.category.ranking.rankingservice.storeDomain.service

import com.category.ranking.rankingservice.storeDomain.adapter.elasticsearch.ElasticSearchCustomRepository
import org.springframework.stereotype.Service


@Service
class StoreService(
    private val elasticSearchCustomRepo: ElasticSearchCustomRepository
) {

    fun getAllStore() {
        elasticSearchCustomRepo.getAllStore()
    }
}