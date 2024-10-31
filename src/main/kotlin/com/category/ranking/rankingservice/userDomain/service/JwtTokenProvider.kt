package com.category.ranking.rankingservice.userDomain.service



import com.category.ranking.rankingservice.userDomain.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*


@Service
class JwtTokenProvider(
    jwtProperties: JwtProperties
) {


    private val secretKey = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )


    fun generationToken(
        email: String,
        expSecond: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String {


        return Jwts.builder()
            .claims()
            .subject(email)
            .issuedAt(Date())
            .expiration(expSecond)
            .add(additionalClaims)
            .and()
            .signWith(secretKey)
            .compact()

    }


    fun isValid(emailFromToken: String, email: String, token: String): Boolean {
        return emailFromToken == email || !isExpired(token)

    }
    fun isExpired(token: String): Boolean =
        getAllClaims(token)
            .expiration
            .before(Date())


    fun extractEmail(token: String): String? =
        getAllClaims(token)
            .subject

    //todo 아래 메서드 검증하기.
    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser()
            .verifyWith(secretKey)
            .build()

        return parser
            .parseSignedClaims(token)
            .payload
    }

}
