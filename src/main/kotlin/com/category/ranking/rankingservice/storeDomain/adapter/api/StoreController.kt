package com.category.ranking.rankingservice.storeDomain.adapter.api

import com.category.ranking.rankingservice.common.adapter.api.ApiResponse
import com.category.ranking.rankingservice.storeDomain.adapter.api.out.StoreResponse
import com.category.ranking.rankingservice.storeDomain.service.StoreService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/stores")
class StoreController(
    private val storeService: StoreService
) {


    @GetMapping("/search/location")
    fun searchStoresByLocation(
        @RequestParam lat: Double,
        @RequestParam lon: Double,
        @RequestParam radius: Int
    ): ResponseEntity<ApiResponse<List<StoreResponse>>> {

        val stores = storeService.searchStoresByLocation(lat, lon, radius)
        return ResponseEntity.ok(ApiResponse.success(stores))

    }

}