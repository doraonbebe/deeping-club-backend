package com.category.ranking.rankingservice.userDomain.infrastructure.repository

import com.category.ranking.rankingservice.userDomain.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByUuid(name: String): User?

    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean

}