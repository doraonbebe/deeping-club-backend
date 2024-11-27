package com.category.ranking.rankingservice.common.enums

import org.springframework.http.HttpStatus

enum class CustomErrorCode(
    val status: HttpStatus,
    val message: String
) {

    USER_PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "password does not match"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),

}