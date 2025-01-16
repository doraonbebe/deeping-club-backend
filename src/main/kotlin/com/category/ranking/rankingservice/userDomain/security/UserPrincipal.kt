package com.category.ranking.rankingservice.userDomain.security

import com.category.ranking.rankingservice.userDomain.domain.Role
import com.category.ranking.rankingservice.userDomain.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class UserPrincipal(
    private val user: User?

) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(Role.USER.name))
    }

    override fun getPassword(): String? {
        return user?.password
    }

    override fun getUsername(): String? {
        return user?.nickName
    }

    fun getUuid(): String {
        return user?.uuid!!
    }

    fun getId(): Long {
        return user?.id!!
    }

    fun getEmail(): String? {
        return user?.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}