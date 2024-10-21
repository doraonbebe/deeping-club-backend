package com.category.ranking.rankingservice.userDomain.security

import com.category.ranking.rankingservice.userDomain.infrastructure.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsService (
    private val userRepository: UserRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(uuid: String?): UserDetails {
        val user = uuid?.let { userRepository.findByEmail(it) }
        return UserPrincipal(user)
    }

    @Throws(UsernameNotFoundException::class)
    fun loadUserByUuid(uuid: String?): UserDetails {
        val user = uuid?.let { userRepository.findByUuid(it) }
        return UserPrincipal(user)
    }


}
