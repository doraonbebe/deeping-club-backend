package com.category.ranking.rankingservice.userDomain.adapter.api

import com.category.ranking.rankingservice.common.adapter.api.CustomApiResponse
import com.category.ranking.rankingservice.common.enums.CustomErrorCode
import com.category.ranking.rankingservice.userDomain.service.UserService
import com.category.ranking.rankingservice.userDomain.service.`in`.SignInDTO
import com.category.ranking.rankingservice.userDomain.service.`in`.SignUpDTO
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.category.ranking.rankingservice.userDomain.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus

@Tag(name = "User API", description = "유저 관련 API")
@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val authService: AuthService

) {

    @Operation(summary = "회원가입 API")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "회원 가입 성공"
        ),
        ApiResponse(
            responseCode = "409",
            description = "이메일 중복"
        )
    ])
    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid signUpDTO: SignUpDTO) : ResponseEntity<CustomApiResponse<String>> {
        val existsEmail = userService.existsUserEmail(signUpDTO.email);
        if (existsEmail) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }

        userService.signUp(signUpDTO);
        return ResponseEntity.ok().body(CustomApiResponse.success(""))
    }

    @Operation(summary = "회원가입 API")
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "회원 가입 성공"
        ),
        ApiResponse(
            responseCode = "401",
            description = "비밀번호 불일치"
        ),
        ApiResponse(
            responseCode = "404",
            description = "이메일 없음"
        )
    ])
    @PostMapping("/sign-in")
    fun signIn(
        @RequestBody @Valid signInDTO: SignInDTO
    ) : ResponseEntity<CustomApiResponse<Any>> {
        val email = signInDTO.email
        val password = signInDTO.password

        val user = userService.findUserByEmail(email) ?:
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CustomApiResponse.error(CustomErrorCode.USER_NOT_FOUND))

        val isMatch = userService.isPasswordMatch(password, user.password!!)
        if (!isMatch) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(CustomApiResponse.error(CustomErrorCode.USER_PASSWORD_NOT_MATCH))
        }

        val jwtToken = authService.authenticate(email, password)

        return ResponseEntity.ok().body(CustomApiResponse.success(jwtToken))
    }

}