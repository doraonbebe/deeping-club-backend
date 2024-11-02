package com.category.ranking.rankingservice.storeDomain.adapter.api.out

import org.springframework.data.elasticsearch.core.geo.GeoPoint

data class StoreResponse(
    val name: String,
    val category: String,
    val location: GeoPoint,
    val address: String,

)
