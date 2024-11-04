package com.category.ranking.rankingservice.storeDomain.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

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

    @JsonManagedReference
    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val likes: MutableList<Likes> = mutableListOf()

) {

}