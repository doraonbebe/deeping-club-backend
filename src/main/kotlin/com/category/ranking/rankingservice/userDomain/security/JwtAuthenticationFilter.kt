package com.category.ranking.rankingservice.userDomain.security

import com.category.ranking.rankingservice.userDomain.service.JwtTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.sql.DriverManager.println
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource


@Component
class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        println("request = [${request}], response = [${response}], filterChain = [${filterChain}]")


        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (authHeader.doesNotContainBearerToken()){
            filterChain.doFilter(request, response)
            return
        }

        val jwtToken = authHeader!!.extractTokenValue()
        val email = jwtTokenProvider.extractEmail(jwtToken)

        if (email != null && SecurityContextHolder.getContext().authentication == null){
            val user = customUserDetailsService.loadUserByUsername(email)
            if (user is UserPrincipal){
                if (user.getUuid()?.let { jwtTokenProvider.isValid(jwtToken, it) } == true){
                    updateContext(user, request)
                }
            }

        }

        filterChain.doFilter(request, response)
    }

    private fun updateContext(user: UserDetails, request: HttpServletRequest){
        val authToken = UsernamePasswordAuthenticationToken(user, null, user.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authToken
    }

    private fun String.extractTokenValue(): String =
        this.substringAfter("Bearer ")

    private fun String?.doesNotContainBearerToken(): Boolean =
        this == null || !this.startsWith("Bearer ")


}