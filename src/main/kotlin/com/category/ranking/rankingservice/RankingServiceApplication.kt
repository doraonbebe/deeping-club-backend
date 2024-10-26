package com.category.ranking.rankingservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class RankingServiceApplication

fun main(args: Array<String>) {
    runApplication<RankingServiceApplication>(*args)
}
