package com.category.ranking.rankingservice.storeDomain.repository

import com.category.ranking.rankingservice.storeDomain.domain.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryJPARepository : JpaRepository<Category, Long> {

}