package com.category.ranking.rankingservice.storeDomain.infrastructure.repository

import com.category.ranking.rankingservice.storeDomain.esDomain.ElasticStore
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface ElasticStoreRepository : ElasticsearchRepository<ElasticStore, String> {

}