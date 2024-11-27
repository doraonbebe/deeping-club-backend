package com.category.ranking.rankingservice.common.adapter.api

import com.category.ranking.rankingservice.common.enums.CustomErrorCode
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

        fun <T> error(customError: CustomErrorCode, details: List<FieldErrorDetails>? = null): CustomApiResponse<T> {
            return CustomApiResponse(
                code = customError.status.value(),
                success = false,
                error = ErrorResponse(customError.message, details)
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