package com.category.ranking.rankingservice.userDomain.service

import com.category.ranking.rankingservice.userDomain.config.JwtProperties
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    private val authManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtProperties: JwtProperties
) {

    fun authenticate(email: String, password: String): String {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                email,
                password
            )
        )
        return jwtTokenProvider.generationToken(
            email,
            Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
        )
    }
}