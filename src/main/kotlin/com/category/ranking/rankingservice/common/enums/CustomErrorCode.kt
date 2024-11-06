package com.category.ranking.rankingservice.common.enums

import org.springframework.http.HttpStatus

enum class CustomErrorCode(
    val status: HttpStatus,
    val message: String
) {


}