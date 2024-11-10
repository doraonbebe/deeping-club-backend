package com.category.ranking.rankingservice.common.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ScanOptions
import org.springframework.stereotype.Service

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, String>
) {



    fun addValueToSet(key: String, value: String) {
        redisTemplate.opsForSet().add(key, value)
    }

    fun removeValueSet(key: String, value: String) {
        redisTemplate.opsForSet().remove(key, value)
    }

    fun isMemberOfSet(key: String, value: String): Boolean? {
        return redisTemplate.opsForSet().isMember(key, value)
    }

    fun getValuesToSet(key: String): MutableSet<String>? {
        return redisTemplate.opsForSet().members(key)
    }


}