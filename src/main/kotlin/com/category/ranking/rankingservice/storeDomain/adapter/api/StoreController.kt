package com.category.ranking.rankingservice.storeDomain.adapter.api

import com.category.ranking.rankingservice.storeDomain.service.StoreService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/stores")
class StoreController(
    private val storeService: StoreService
) {


    @GetMapping
    fun getAllStore(): ResponseEntity<Unit> {
        val stores = storeService.getAllStore()
        return ResponseEntity.ok(stores)
    }
}