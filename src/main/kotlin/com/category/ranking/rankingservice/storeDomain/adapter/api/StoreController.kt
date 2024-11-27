package com.category.ranking.rankingservice.storeDomain.adapter.api


import com.category.ranking.rankingservice.common.adapter.api.CustomApiResponse
import com.category.ranking.rankingservice.common.utils.getClientIp
import com.category.ranking.rankingservice.storeDomain.adapter.api.`in`.LikeRequest
import com.category.ranking.rankingservice.storeDomain.adapter.api.`in`.ReviewCreateRequest
import com.category.ranking.rankingservice.storeDomain.adapter.api.`in`.ReviewUpdateRequest
import com.category.ranking.rankingservice.storeDomain.adapter.api.out.database.CategoryResponse
import com.category.ranking.rankingservice.storeDomain.adapter.api.out.elasticsearch.StoreResponse
import com.category.ranking.rankingservice.storeDomain.service.StoreLikesService
import com.category.ranking.rankingservice.storeDomain.service.StoreReviewsService
import com.category.ranking.rankingservice.storeDomain.service.StoreService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Store API", description = "상점 관련 API")
@RestController
@RequestMapping("/api/v1/stores")
class StoreController(
    private val storeService: StoreService,
    private val storeReviewsService: StoreReviewsService,
    private val storeLikesService: StoreLikesService,
) {


    @Operation(
        summary = "위치 기반 상점 목록 검색 API",
        description = "위치(lat, lon)의 반경(radius)과 카테고리를 포함하여 상점을 검색합니다.",
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "검색한 상점 목록을 반환합니다."
        ),
        ApiResponse(
            responseCode = "400",
            description = "잘못된 요청"
        )
    ])
    @GetMapping("/search/location")
    fun searchStoresByLocation(
        @Parameter(description = "위도", example = "37.5482359") @RequestParam lat: Double,
        @Parameter(description = "경도", example = "126.9397607") @RequestParam lon: Double,
        @Parameter(description = "카테고리", example = "CAFE") @RequestParam(required = false) category: String,
        @Parameter(description = "검색 반경 (단위 : m)", example = "1000") @RequestParam(defaultValue = "300") @Min(300) @Max(1000) radius: Int,
    ): ResponseEntity<CustomApiResponse<List<StoreResponse>>> {
        val stores = storeService.searchStoresByLocation(lat, lon, radius, category)
        return ResponseEntity.ok(CustomApiResponse.success(stores))

    }

    @Operation(
        summary = "위치 기반 Top 5 상점 검색 API",
        description = "위치(lat, lon)의 반경(radius)에 기반하여 상점을 검색합니다.",
    )
    @GetMapping("/search/location/limit")
    fun searchStoresByLocationLimit(
        @Parameter(description = "위도", example = "37.5482359") @RequestParam lat: Double,
        @Parameter(description = "경도", example = "126.9397607") @RequestParam lon: Double,
        @Parameter(description = "검색 반경 (단위 : m)", example = "1000") @RequestParam(defaultValue = "300") @Min(300) @Max(1000) radius: Int,
        @Parameter(description = "상점 검색 수", example = "5") @RequestParam(defaultValue = "5") @Min(5) @Max(10) limit: Int,
    ): ResponseEntity<CustomApiResponse<List<StoreResponse>>> {
        val stores = storeService.searchStoresByLocationWithLimit(lat, lon, radius, limit)
        return ResponseEntity.ok(CustomApiResponse.success(stores))

    }

    @GetMapping("/category")
    fun findStoreCategory(): ResponseEntity<CustomApiResponse<List<CategoryResponse>>> {
        val category = storeService.findAllCategory();
        return ResponseEntity.ok(CustomApiResponse.success(category))
    }

    @PostMapping("/like")
    fun likeStore(@RequestBody likeRequest: LikeRequest): ResponseEntity<Any> {
        storeLikesService.saveLike(likeRequest.uuid, likeRequest.userId)
        return ResponseEntity.ok().body(true)
    }


    //TODO: 좋아요는 로그인 한 유저만.
    @PostMapping("/like2")
    fun likeStore2(@RequestBody likeRequest: LikeRequest): ResponseEntity<CustomApiResponse<Boolean>> {

        return ResponseEntity.ok()
            .body(
                CustomApiResponse.success(
                    storeLikesService.saveLike2(likeRequest.uuid, likeRequest.userId)
                )
            )
    }

    @PostMapping("/{uuid}/views")
    fun saveStoreView(
        @PathVariable uuid: String,
        request: HttpServletRequest
    ): ResponseEntity<CustomApiResponse<Boolean>> {
        val clientIp = request.getClientIp()
        return ResponseEntity.ok()
            .body(
                CustomApiResponse.success(
                    storeService.saveView(clientIp, uuid)
                )
            )
    }

    /* 로그인 한 유저만 리뷰를 작성할 수 있는지 기획 확정 필요. 만약 로그인 하지 않은 유저도 작성할 수 있다면, 수정, 삭제는? */

    @PostMapping("/{uuid}/review")
    fun saveStoreReview(
        @PathVariable uuid: String,
        @RequestBody @Valid reviewCreateRequest: ReviewCreateRequest
    ): ResponseEntity<CustomApiResponse<Boolean>>{
        storeReviewsService.saveStoreReview(uuid,  reviewCreateRequest)

        return ResponseEntity.ok().body(CustomApiResponse.success(true))
    }

    @PutMapping("/{uuid}/review/{id}")
    fun updateStoreReview(
        @PathVariable uuid: String,
        @PathVariable id: Long,
        @RequestBody @Valid reviewUpdateRequest: ReviewUpdateRequest
    ): ResponseEntity<CustomApiResponse<Boolean>>{
        storeReviewsService.updateStoreReview(uuid, id, reviewUpdateRequest)
        return ResponseEntity.ok().body(CustomApiResponse.success(true))
    }

    @DeleteMapping("/{uuid}/review/{id}")
    fun deleteStoreReview(
        @PathVariable uuid: String,
        @PathVariable id: Long
    ): ResponseEntity<CustomApiResponse<Boolean>>{
        storeReviewsService.softDeleteStoreReview(uuid, id)
        return ResponseEntity.ok().body(CustomApiResponse.success(true))
    }

}