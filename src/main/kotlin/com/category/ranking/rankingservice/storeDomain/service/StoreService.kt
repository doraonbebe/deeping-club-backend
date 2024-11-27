package com.category.ranking.rankingservice.storeDomain.service

import com.category.ranking.rankingservice.common.enums.RedisKeys
import com.category.ranking.rankingservice.common.service.RedisService
import com.category.ranking.rankingservice.storeDomain.adapter.api.out.database.CategoryResponse
import com.category.ranking.rankingservice.storeDomain.adapter.api.out.elasticsearch.StoreResponse
import com.category.ranking.rankingservice.storeDomain.adapter.elasticsearch.ElasticSearchCustomRepository
import com.category.ranking.rankingservice.storeDomain.adapter.infrastructure.StoreRepository
import com.category.ranking.rankingservice.storeDomain.domain.Store
import com.category.ranking.rankingservice.storeDomain.repository.CategoryJPARepository
import com.category.ranking.rankingservice.storeDomain.repository.StoreJPARepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class StoreService(
    private val elasticSearchCustomRepo: ElasticSearchCustomRepository,
    private val storeJPARepo: StoreJPARepository,
    private val categoryJPARepo: CategoryJPARepository,
    private val redisService: RedisService,
    private val storeRepo: StoreRepository,

    ) {

    fun searchStoresByLocationWithLimit(lat: Double, lon: Double, radius: Int, limit: Int): List<StoreResponse> {
        val recommendedStoresLimit = 3

        val recommendedStores = elasticSearchCustomRepo.findStoresByRadiusLimit(lat, lon, radius, recommendedStoresLimit)
        val recommendedStoresCnt = recommendedStores.size

        val topLikedStoresLimit = limit - recommendedStoresCnt
        val uuids = recommendedStores.map { it.uuid }
        val topLikedStores = storeRepo.findTopStoresByLikeCnt(uuids, topLikedStoresLimit)

        //TODO: toStoreResponse() -> distance를 가지고 오기 위해 ES 호출 1번 더 해야함.
        val convertedTopLikedStores = topLikedStores.map { it.toStoreResponse() }

        val top5Stores = recommendedStores + convertedTopLikedStores
        return top5Stores
    }



    fun searchStoresByLocation(lat: Double, lon: Double, radius: Int, category: String): List<StoreResponse> {
        return elasticSearchCustomRepo.findStoresByRadiusAndCategory(lat, lon, radius, category)
    }

    @Transactional(readOnly = true)
    fun findAllCategory(): List<CategoryResponse> {
        return categoryJPARepo.findAll().map { category ->
            CategoryResponse(
                id = category.id,
                category = category.category
            )
        }
    }


    fun saveView(clientIp: String, uuid: String): Boolean {
        val viewKey = "${RedisKeys.STORE_VIEWS.value}$uuid"
        redisService.addValueToSet(viewKey, clientIp)
        return true
    }

    @Transactional(readOnly = true)
    fun findStoreByUuid(uuid: String): Store? {
        return storeJPARepo.findByUuid(uuid)
    }


    @Transactional(readOnly = true)
    fun findByUuids(uuids: List<String>): List<Store> {
        return storeJPARepo.findByUuidIn(uuids)
    }


    @Transactional
    fun saveAllStore(stores: List<Store>) {
        storeJPARepo.saveAll(stores)
    }


}