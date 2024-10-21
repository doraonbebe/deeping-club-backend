package com.category.ranking.rankingservice.userDomain.service.`in`

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignInDTO(
    @field:Email(message = "email valid")
    val email: String,
    @field:NotBlank(message = "password notblank")
    var password: String

) {

}