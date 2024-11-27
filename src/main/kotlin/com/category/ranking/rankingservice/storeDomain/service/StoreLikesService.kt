package com.category.ranking.rankingservice.storeDomain.service

import com.category.ranking.rankingservice.common.enums.RedisKeys
import com.category.ranking.rankingservice.common.service.RedisService
import com.category.ranking.rankingservice.storeDomain.domain.Likes
import com.category.ranking.rankingservice.storeDomain.repository.LikesRepository
import org.springframework.stereotype.Service

@Service
class StoreLikesService(
    private val storeService: StoreService,
    private val likesRepo: LikesRepository,
    private val redisService: RedisService
) {


    fun saveLike(uuid: String, userId: Long) {
        storeService.findStoreByUuid(uuid)?.let { store ->
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
}