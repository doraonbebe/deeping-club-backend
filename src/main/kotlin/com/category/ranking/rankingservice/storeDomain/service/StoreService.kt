package com.category.ranking.rankingservice.storeDomain.service

import com.category.ranking.rankingservice.common.enums.RedisKeys
import com.category.ranking.rankingservice.common.service.RedisService
import com.category.ranking.rankingservice.storeDomain.adapter.api.out.database.CategoryResponse
import com.category.ranking.rankingservice.storeDomain.adapter.api.out.elasticsearch.StoreResponse
import com.category.ranking.rankingservice.storeDomain.adapter.elasticsearch.ElasticSearchCustomRepository
import com.category.ranking.rankingservice.storeDomain.adapter.infrastructure.StoreRepository
import com.category.ranking.rankingservice.storeDomain.domain.Likes
import com.category.ranking.rankingservice.storeDomain.domain.Store
import com.category.ranking.rankingservice.storeDomain.repository.CategoryJPARepository
import com.category.ranking.rankingservice.storeDomain.repository.LikesRepository
import com.category.ranking.rankingservice.storeDomain.repository.StoreJPARepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class StoreService(
    private val elasticSearchCustomRepo: ElasticSearchCustomRepository,
    private val storeJPARepo: StoreJPARepository,
    private val categoryJPARepo: CategoryJPARepository,
    private val likesRepo: LikesRepository,
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

    fun saveLike(uuid: String, userId: Long) {

        storeJPARepo.findByUuid(uuid)?.let { store ->
            val existsLike = likesRepo.existsByStoreAndUserId(store, userId)
            if (existsLike) return

            likesRepo.save(Likes.createLike(store, userId))

        }
    }

    fun saveLike2(uuid: String, userId: Long): Boolean {
        val likeKey = "${RedisKeys.STORE_LIKES.value}$uuid"
        val hasLiked = redisService.isMemberOfSet(likeKey, userId.toString())

        if (hasLiked == true) {
            redisService.removeValueSet(likeKey, userId.toString())
            return true;
        } else {
            redisService.addValueToSet(likeKey, userId.toString())
            return false
        }
    }

    fun saveView(clientIp: String, uuid: String): Boolean {
        val viewKey = "${RedisKeys.STORE_VIEWS.value}$uuid"
        redisService.addValueToSet(viewKey, clientIp)
        return true
    }

    @Transactional(readOnly = true)
    fun findByUuid(uuid: String): Store {
        return storeJPARepo.findByUuid(uuid) ?: throw EntityNotFoundException("Store $uuid not found")
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