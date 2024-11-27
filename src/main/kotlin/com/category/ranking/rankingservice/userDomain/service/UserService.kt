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
    fun findUserByEmail(email: String) : User? {
        return userRepository.findByEmail(email)
    }

    @Transactional(readOnly = true)
    fun existsUserEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    fun isPasswordMatch(rowPassword: String, encodedPassword: String) : Boolean {
        return passwordEncoder.matches(rowPassword, encodedPassword)
    }


}