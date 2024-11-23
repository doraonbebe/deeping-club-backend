package com.category.ranking.rankingservice.storeDomain.domain

import com.category.ranking.rankingservice.common.domain.BaseEntity
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "category")
class Category(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "category", nullable = false)
    val category: String,

    @JsonManagedReference
    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val stores: MutableList<Store> = mutableListOf()

    ): BaseEntity() {


}

