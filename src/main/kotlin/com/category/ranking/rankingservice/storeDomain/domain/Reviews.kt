package com.category.ranking.rankingservice.storeDomain.domain

import com.category.ranking.rankingservice.common.domain.BaseEntity
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "reviews")
class Reviews(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    val store: Store,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "status", nullable = false)
    val status: Status,

    @Column(name = "content")
    val content: String,

    @Column(name = "rating", nullable = false)
    val rating: Double = 0.0,

): BaseEntity() {

    fun addReview() {
        store.reviews.add(this)
    }

    companion object {
        fun createReview(
            store: Store,
            userId: Long,
            status: Status,
            content: String,
            rating: Double,
        ): Reviews {
            val review = Reviews(
                store = store,
                userId = userId,
                status = status,
                content = content,
                rating = rating
            )
            review.addReview()
            return review
        }
    }


}