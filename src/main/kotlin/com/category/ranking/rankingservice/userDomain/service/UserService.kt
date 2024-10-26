package com.category.ranking.rankingservice.userDomain.service

import com.category.ranking.rankingservice.userDomain.domain.User
import com.category.ranking.rankingservice.userDomain.infrastructure.repository.UserRepository
import com.category.ranking.rankingservice.userDomain.service.`in`.SignUpDTO
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
)

{
    @Transactional
    fun signUp(signUpDTO: SignUpDTO){
        val encodedPassword = passwordEncoder.encode(signUpDTO.password)
        val user = signUpDTO.toEntity(encodedPassword)

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    fun findByUserEmail(email: String) : User {
        return userRepository.findByEmail(email) ?: throw UsernameNotFoundException("user not find")
    }

    fun isPasswordMatch(rowPassword: String, encodedPassword: String) : Boolean {
        return passwordEncoder.matches(rowPassword, encodedPassword)
    }


}