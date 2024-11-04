package com.category.ranking.rankingservice.common.exception

import com.category.ranking.rankingservice.common.adapter.api.CustomApiResponse
import com.category.ranking.rankingservice.common.adapter.api.ErrorResponse
import com.category.ranking.rankingservice.common.adapter.api.FieldErrorDetails
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<CustomApiResponse<Unit>> {
        val errors = ex.bindingResult
            .allErrors
            .filterIsInstance<FieldError>()
            .map { error ->
                FieldErrorDetails(
                    field = error.field,
                    message = error.defaultMessage ?: "Invalid value"
                )
            }

        val errorResponse = ErrorResponse(
            message = "Validation failed",
            details = errors
        )

        val apiResponse = CustomApiResponse<Unit>(
            code = HttpStatus.BAD_REQUEST.value(),
            success = false,
            error = errorResponse
        )

        return ResponseEntity(apiResponse, HttpStatus.BAD_REQUEST)
    }
}