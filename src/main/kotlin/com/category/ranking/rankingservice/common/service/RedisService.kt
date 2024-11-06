package com.category.ranking.rankingservice.common.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, String>
) {


    fun save(key: String, value: String) {
        redisTemplate.opsForValue().set(key, value)
    }


}