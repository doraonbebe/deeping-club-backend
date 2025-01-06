package com.category.ranking.rankingservice.storeDomain.domain

import com.category.ranking.rankingservice.common.domain.BaseEntity
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "review_attachments")
class ReviewAttachments(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    val reviews: Reviews,

    @Column(name = "image_url", nullable = false)
    val imageUrl: String,


    ) : BaseEntity() {



}