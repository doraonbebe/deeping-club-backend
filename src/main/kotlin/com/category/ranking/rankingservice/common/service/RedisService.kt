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

    fun getValueCntOfSet(key: String): Long? {
       return redisTemplate.opsForSet().size(key)
    }

    fun getValuesToSet(key: String): Set<String>? {
        return redisTemplate.opsForSet().members(key)
    }

    fun getAllValuesOfKeys(pattern: String): Map<String, Set<String>?> {
        val keys = getKeysByPattern(pattern)
        if (keys.isNullOrEmpty()) return emptyMap()

        val resultMap = mutableMapOf<String, Set<String>?>()
        for (key in keys) {
            val listValues = getValuesToSet(key)
            resultMap[key] = listValues
        }
        return resultMap
    }

    fun countValuesByKeys(pattern: String): Map<String, Long> {
        val keys = getKeysByPattern(pattern)
        val keyValueCounts = mutableMapOf<String, Long>()

        keys?.forEach { key ->
            val cnt = getValueCntOfSet(key) ?: 0L
            keyValueCounts[key] = cnt
        }

        return keyValueCounts
    }

    private fun getKeysByPattern(pattern: String): MutableSet<String>? {
        val options = ScanOptions.scanOptions().match(pattern).count(100).build()
        val cursor = redisTemplate.execute { connection ->
            connection.scan(options)
        }
        val keys = mutableSetOf<String>()
        cursor?.forEach { keys.add(String(it)) }
        return keys
    }


}