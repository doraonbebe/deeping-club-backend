package com.category.ranking.rankingservice.storeDomain.repository

import com.category.ranking.rankingservice.storeDomain.domain.Likes
import com.category.ranking.rankingservice.storeDomain.domain.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LikesRepository : JpaRepository<Likes, Long> {

    fun existsByStoreAndUserId(store: Store, userId: Long): Boolean
}