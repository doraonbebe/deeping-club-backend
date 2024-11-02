package com.category.ranking.rankingservice.userDomain.adapter.api

import com.category.ranking.rankingservice.userDomain.service.UserService
import com.category.ranking.rankingservice.userDomain.service.`in`.SignInDTO
import com.category.ranking.rankingservice.userDomain.service.`in`.SignUpDTO
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.category.ranking.rankingservice.userDomain.service.AuthService
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "User API", description = "유저 관련 API")
@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val authService: AuthService

) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid signUpDTO: SignUpDTO) : ResponseEntity<Any> {
        //유효성 검증 등 기획서 나오면 진행
        userService.signUp(signUpDTO);
        return ResponseEntity.ok().body("")
    }

    @PostMapping("/sign-in")
    fun signIn(
        @RequestBody @Valid signInDTO: SignInDTO
    ) : ResponseEntity<Any> {
        //유효성 검증 등 기획서 나오면 진행
        val email = signInDTO.email
        val password = signInDTO.password

        val user = userService.findByUserEmail(email)
        if (user != null) {
            //todo
        }

        val isMatch = userService.isPasswordMatch(email, password)
        if (!isMatch) {
            //todo
        }

        val jwtToken = authService.authenticate(email, password)

        return ResponseEntity.ok().body(jwtToken)
    }

}