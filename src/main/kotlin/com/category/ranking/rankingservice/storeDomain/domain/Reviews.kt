package com.category.ranking.rankingservice.storeDomain.domain

import com.category.ranking.rankingservice.common.domain.BaseEntity
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.time.LocalDate

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: Status,

    @Column(name = "content")
    var content: String,

    @Column(name = "rating", nullable = false)
    var rating: Double = 0.0,

    @Column(name = "written_at", nullable = false)
    var writtenAt: LocalDate,

    @JsonManagedReference
    @OneToMany(mappedBy = "reviews", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    val reviewAttachments: MutableList<ReviewAttachments> = mutableListOf()


): BaseEntity() {

    fun updateReview(
        status: Status,
        content: String,
        rating: Double,
        writtenAt: LocalDate,
        imageUrls: MutableList<String>
    ){
        this.status = status
        this.content = content
        this.rating = rating
        this.writtenAt = writtenAt

        this.reviewAttachments.clear()

        val attachments = imageUrls.map { imageUrl ->
            ReviewAttachments(
                reviews = this,
                imageUrl = imageUrl
            )
        }
        this.reviewAttachments.addAll(attachments)

    }

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
            writtenAt: LocalDate,
            imageUrls: MutableList<String>
        ): Reviews {
            val review = Reviews(
                store = store,
                userId = userId,
                status = status,
                content = content,
                rating = rating,
                writtenAt = writtenAt
            )

            val attachments = imageUrls.map { imageUrl ->
                ReviewAttachments(
                    reviews = review,
                    imageUrl = imageUrl
                )
            }
            review.reviewAttachments.addAll(attachments)

            review.addReview()
            return review
        }
    }


}