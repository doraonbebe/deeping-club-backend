package com.category.ranking.rankingservice.storeDomain.service

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
    private val likesRepo: LikesRepository

) {

    fun searchStoresByLocation(lat: Double, lon: Double, radius: Int): List<StoreResponse> {
        return elasticSearchCustomRepo.findStoresByLocationAndDistance(lat, lon, radius)
    }

    fun saveLike(id: Long, userId: Long){

        val findStore = storeRepo.findById(id)

        if (findStore.isPresent){
            val store = findStore.get()
            val existsLike = likesRepo.existsByStoreAndUserId(store, userId)
            if (!existsLike) {
                likesRepo.save(Likes.createLike(store, userId))
            }
        }
    }

}