package com.category.ranking.rankingservice.storeDomain.adapter.api.out.elasticsearch

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ElasticsearchHitResponse<T>(
    val _source: T,
    val sort: List<Double>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ElasticsearchHits<T>(
    val hits: List<ElasticsearchHitResponse<T>>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ElasticsearchResponse<T>(
    val hits: ElasticsearchHits<T>
)