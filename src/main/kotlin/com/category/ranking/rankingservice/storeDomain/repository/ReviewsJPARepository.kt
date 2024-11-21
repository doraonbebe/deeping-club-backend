package com.category.ranking.rankingservice.storeDomain.repository

import com.category.ranking.rankingservice.storeDomain.domain.Reviews
import com.category.ranking.rankingservice.storeDomain.domain.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewsJPARepository : JpaRepository<Reviews, Long> {

    fun findByUserIdAndStore(userId: Long, store: Store): Reviews?

}