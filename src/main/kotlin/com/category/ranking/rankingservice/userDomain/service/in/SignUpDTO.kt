package com.category.ranking.rankingservice.userDomain.service.`in`

import com.category.ranking.rankingservice.userDomain.domain.Role
import com.category.ranking.rankingservice.userDomain.domain.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignUpDTO(
    @field:NotBlank(message = "Username cannot be empty")
    val nickName: String,

    @field:Email(message = "Username cannot be empty")
    val email: String,

    @field:NotBlank(message = "Userpassowrd cannot be empty")
    var password: String,

) {
    
    fun toEntity(encoderPassword: String): User {
        return User(
            nickName = this.nickName,
            email = this.email,
            role = Role.USER,
            password = encoderPassword
        )
    }

}