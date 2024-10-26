package com.category.ranking.rankingservice.userDomain.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "user")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "email", nullable = false)
    var email: String? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "password", nullable = false)
    var password: String? = null,

    @Column(name = "date_of_brith")
    var dateOfBirth: LocalDate? = null,

    @Column(name = "uuid", nullable = false, unique = true)
    val uuid: String = UUID.randomUUID().toString(),

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    val role: Role? = Role.USER,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
){


}