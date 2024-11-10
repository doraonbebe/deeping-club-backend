package com.category.ranking.rankingservice.common.utils

import jakarta.servlet.http.HttpServletRequest


fun HttpServletRequest.getClientIp(): String {
    val xForwardedFor = this.getHeader("X-Forwarded-For")
    return if (xForwardedFor != null && xForwardedFor.isNotEmpty()) {
        xForwardedFor.split(",")[0]
    } else {
        this.remoteAddr.takeIf { it != "0:0:0:0:0:0:0:1" } ?: "127.0.0.1"
    }
}