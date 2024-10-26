package com.category.ranking.rankingservice.userDomain.security
import com.category.ranking.rankingservice.userDomain.config.JwtProperties
import com.category.ranking.rankingservice.userDomain.infrastructure.repository.UserRepository
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.core.userdetails.UserDetailsService

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val AuthenticationProvider: AuthenticationProvider,
//    private val customUserDetailsService: CustomUserDetailsService
){



    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .authorizeHttpRequests{
                it
                    .anyRequest().permitAll()
            }
            .sessionManagement { it
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(AuthenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
//        http {
//            csrf { disable() }
//            httpBasic { disable() }
//            formLogin { disable() }
//            authorizeHttpRequests {
//                authorize(anyRequest, permitAll)
////                authorize(AntPathRequestMatcher("/h2-console/**"), permitAll)
////                authorize(AntPathRequestMatcher("/swagger-ui/**"), permitAll)
////                authorize(AntPathRequestMatcher("/v1/api/**"), permitAll)
////                authorize(AntPathRequestMatcher("/health-check/**"), permitAll)
////                authorize(anyRequest, authenticated)
//            }
//            sessionManagement {
//                sessionCreationPolicy = SessionCreationPolicy.STATELESS
//            }
//            authenticationProvider()
//            addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
//
//        }
//        return http.build()





}