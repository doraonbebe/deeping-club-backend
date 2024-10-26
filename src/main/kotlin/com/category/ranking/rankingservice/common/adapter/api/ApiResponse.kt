package com.category.ranking.rankingservice.common.adapter.api

data class ApiResponse<T>(
    val code: Int,
    val success: Boolean,
    val data: T? = null,
    val error: ErrorResponse? = null,
    val timestamp: String = java.time.Instant.now().toString()
)

data class ErrorResponse(
    val message: String,
    val details: List<FieldErrorDetails>? = null
)

data class FieldErrorDetails(
    val field: String,
    val message: String
)