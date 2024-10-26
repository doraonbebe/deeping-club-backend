package com.category.ranking.rankingservice.userDomain.service

import com.category.ranking.rankingservice.userDomain.config.JwtProperties
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import java.util.*
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class JwtTokenProviderTests {


    private lateinit var jwtTokenProvider: JwtTokenProvider
    private lateinit var jwtProperties: JwtProperties
    private val secretKey = "A0NpEOTIOJOjdHep2ynGwKhtTqPyJmD6XCAsYH2G9w3EOK5rDq"
    private val email = "toywar4@gmail.com"
    private val expSecond = 60000L //1000분
    


    @BeforeEach
    fun setUp(){
        jwtProperties = Mockito.mock(JwtProperties::class.java)
        Mockito.`when`(jwtProperties.key).thenReturn(secretKey)

        jwtTokenProvider = JwtTokenProvider(jwtProperties)
    }

    @Test
    fun create_generation_token_test() {
        val token = jwtTokenProvider.generationToken(email, Date(System.currentTimeMillis() + expSecond))
        println(token)
        assertNotNull(token)
    }

    @Test
    fun verify_token_uuid_test() {
        val token = jwtTokenProvider.generationToken(email, Date(System.currentTimeMillis() + expSecond))
        println(token)
        val isValidToken = jwtTokenProvider.isValid(token, email)

        assertTrue(isValidToken)
    }
}