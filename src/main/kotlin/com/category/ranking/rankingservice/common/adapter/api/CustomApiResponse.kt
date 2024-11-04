package com.category.ranking.rankingservice.common.adapter.api

import org.springframework.http.HttpStatus

data class CustomApiResponse<T>(
    val code: Int,
    val success: Boolean,
    val data: T? = null,
    val error: ErrorResponse? = null,
    val timestamp: String = java.time.Instant.now().toString()
){
    companion object{
        fun <T> success(data: T): CustomApiResponse<T> {
            return CustomApiResponse(
                code = HttpStatus.OK.value(),
                success = true,
                data = data
            )
        }

        fun <T> error(message: String, details: List<FieldErrorDetails>? = null): CustomApiResponse<T> {
            return CustomApiResponse(
                code = HttpStatus.BAD_REQUEST.value(),
                success = false,
                error = ErrorResponse(message, details)
            )
        }
    }
}

data class ErrorResponse(
    val message: String,
    val details: List<FieldErrorDetails>? = null
)

data class FieldErrorDetails(
    val field: String,
    val message: String
)