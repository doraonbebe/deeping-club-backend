package com.category.ranking.rankingservice.storeDomain.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "likes")
class Likes(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "store_id", nullable = false)
    val store: Store,

    @Column(name = "user_id", nullable = false)
    val userId: Long

){
    fun addStore() {
        store.likes.add(this)
    }
    companion object {
        fun createLike(store: Store, userId: Long): Likes {
            val like = Likes(
                store = store,
                userId = userId,
            )
            like.addStore()
            return like
        }
    }
}

