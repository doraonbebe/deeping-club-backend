package com.category.ranking.rankingservice.userDomain.service.`in`

import com.category.ranking.rankingservice.userDomain.domain.Role
import com.category.ranking.rankingservice.userDomain.domain.User
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class SignUpDTO(
    @field:NotBlank(message = "Username cannot be empty")
    val name: String,

    @field:Email(message = "Username cannot be empty")
    val email: String,

    @field:NotBlank(message = "Userpassowrd cannot be empty")
    var password: String,

    @field:Nullable
    val dateOfBirth: LocalDate? = null
) {
    
    fun toEntity(encoderPassword: String): User {
        return User(
            name = this.name,
            email = this.email,
            role = Role.USER,
            password = encoderPassword,
            dateOfBirth = this.dateOfBirth,
        )
    }

}