package com.category.ranking.rankingservice.storeDomain.service

import com.category.ranking.rankingservice.common.service.RedisService
import com.category.ranking.rankingservice.storeDomain.adapter.api.out.StoreResponse
import com.category.ranking.rankingservice.storeDomain.adapter.elasticsearch.ElasticSearchCustomRepository
import com.category.ranking.rankingservice.storeDomain.domain.Likes
import com.category.ranking.rankingservice.storeDomain.infrastructure.repository.LikesRepository
import com.category.ranking.rankingservice.storeDomain.infrastructure.repository.StoreRepository
import org.springframework.stereotype.Service


@Service
class StoreService(
    private val elasticSearchCustomRepo: ElasticSearchCustomRepository,
    private val storeRepo: StoreRepository,
    private val likesRepo: LikesRepository,
    private val redisService: RedisService

) {

    fun searchStoresByLocation(lat: Double, lon: Double, radius: Int): List<StoreResponse> {
        return elasticSearchCustomRepo.findStoresByLocationAndDistance(lat, lon, radius)
    }

    fun saveLike(uuid: String, userId: Long) {

        storeRepo.findByUuid(uuid)?.let {store ->
            val existsLike = likesRepo.existsByStoreAndUserId(store, userId)
            if (existsLike) return

            likesRepo.save(Likes.createLike(store, userId))
        }
    }

    fun saveLike2(uuid: String, userId: Long){
        val store = storeRepo.findByUuid(uuid)?: throw NoSuchElementException("store not find with uuid: $uuid")

        val existsLike = likesRepo.existsByStoreAndUserId(store, userId)
        if (existsLike) return

        likesRepo.save(Likes.createLike(store, userId))

        val likeKey = "store_likes:$uuid"
        redisService.save(likeKey, userId.toString())

    }
}