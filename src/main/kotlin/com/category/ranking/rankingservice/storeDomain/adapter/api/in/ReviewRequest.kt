package com.category.ranking.rankingservice.storeDomain.adapter.api.`in`

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Schema(name = "가게 리뷰 등록")
data class ReviewCreateRequest(
    @field:NotNull(message = "userId 필수")
    val userId: Long,

    @field:NotBlank(message = "content 빈 공백을 허용하지 않음")
    val content: String,

    @field:NotNull(message = "rating 필수")
    @field:DecimalMin(value = "1.0", inclusive = true)
    @field:DecimalMax(value = "5.0", inclusive = true)
    val rating: Double
)

@Schema(name = "가게 리뷰 수정")
data class ReviewUpdateRequest(
    @field:NotNull(message = "userId 필수")
    val userId: Long,

    @field:NotBlank(message = "content 빈 공백을 허용하지 않음")
    val content: String,

    @field:NotNull(message = "rating 필수")
    @field:DecimalMin(value = "1.0", inclusive = true)
    @field:DecimalMax(value = "5.0", inclusive = true)
    val rating: Double
)