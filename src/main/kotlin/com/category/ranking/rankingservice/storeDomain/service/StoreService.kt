package com.category.ranking.rankingservice.storeDomain.service

import com.category.ranking.rankingservice.common.service.RedisService
import com.category.ranking.rankingservice.storeDomain.adapter.api.out.StoreResponse
import com.category.ranking.rankingservice.storeDomain.adapter.elasticsearch.ElasticSearchCustomRepository
import com.category.ranking.rankingservice.storeDomain.domain.Likes
import com.category.ranking.rankingservice.storeDomain.domain.Store
import com.category.ranking.rankingservice.storeDomain.infrastructure.repository.LikesRepository
import com.category.ranking.rankingservice.storeDomain.infrastructure.repository.StoreRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class StoreService(
    private val elasticSearchCustomRepo: ElasticSearchCustomRepository,
    private val storeRepo: StoreRepository,
    private val likesRepo: LikesRepository,
    private val redisService: RedisService,

) {

    fun searchStoresByLocation(lat: Double, lon: Double, radius: Int): List<StoreResponse> {
        return elasticSearchCustomRepo.findStoresByLocationAndDistance(lat, lon, radius)
    }

    fun saveLike(uuid: String, userId: Long) {

        storeRepo.findByUuid(uuid)?.let { store ->
            val existsLike = likesRepo.existsByStoreAndUserId(store, userId)
            if (existsLike) return

            likesRepo.save(Likes.createLike(store, userId))

        }
    }

    fun saveLike2(uuid: String, userId: Long): Boolean {
        val likeKey = "store_likes:$uuid"
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
        val viewKey = "views_$uuid"
        redisService.addValueToSet(viewKey, clientIp)
        return true
    }


    @Transactional(readOnly = true)
    fun findAllStore(): List<Store> {
        return storeRepo.findAll()
    }

    fun findByUuid(uuid: String): Store? {
        return storeRepo.findByUuid(uuid)
    }

    fun saveLikeAll(store: Store, userIds: List<Long>) {
//        Likes.createLikes()
//        likesRepo.saveAll();
    }

}