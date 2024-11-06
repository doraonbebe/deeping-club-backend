package com.category.ranking.rankingservice.storeDomain.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "store")
class Store(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "name")
    val name: String? = "",

    @Column(name = "category")
    val category: String,

    @Column(name = "address")
    val address: String,

    @Column(name = "latitude")
    val latitude: Double,

    @Column(name = "longitude")
    val longitude: Double,

    @Column(name = "uuid", nullable = false, unique = true)
    val uuid: String = UUID.randomUUID().toString(),

    @JsonManagedReference
    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val likes: MutableList<Likes> = mutableListOf()

) {

}