package com.category.ranking.rankingservice.storeDomain.adapter.api.out.database

import com.category.ranking.rankingservice.storeDomain.adapter.api.out.elasticsearch.StoreResponse
import org.springframework.data.elasticsearch.core.geo.GeoPoint

data class StoreResponse(
    val name: String,
    val category: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val uuid: String,
){
    fun toStoreResponse(): StoreResponse {
        return StoreResponse(
            name = this.name,
            category = this.category,
            location = GeoPoint(this.latitude, this.longitude),
            address = this.address,
            uuid = this.uuid
        )
    }
}
