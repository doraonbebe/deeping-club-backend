package com.category.ranking.rankingservice.storeDomain.infrastructure.repository

import com.category.ranking.rankingservice.storeDomain.domain.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreRepository : JpaRepository<Store, Long> {
    fun findByUuid(uuid: String): Store?
    fun findByUuidIn(uuids: List<String>): List<Store>

}