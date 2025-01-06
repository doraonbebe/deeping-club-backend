package com.category.ranking.rankingservice.storeDomain.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "store")
class Store(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,

    @Column(name = "name")
    val name: String? = "",

    @Column(name = "address")
    val address: String,

    @Column(name = "latitude")
    val latitude: Double,

    @Column(name = "longitude")
    val longitude: Double,

    @Column(name = "views_cnt", nullable = false, columnDefinition = "INT DEFAULT 0")
    var viewsCnt: Int,

    @Column(name = "likes_cnt", nullable = false, columnDefinition = "INT DEFAULT 0")
    var likesCnt: Int,

    @Column(name = "uuid", nullable = false, unique = true)
    val uuid: String = UUID.randomUUID().toString(),

    @JsonManagedReference
    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val likes: MutableSet<Likes> = HashSet(),

    @JsonManagedReference
    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val reviews: MutableList<Reviews> = mutableListOf()

) {

    fun addLike(userId: Long){
        val like = Likes.createLike(this, userId)
        likes.add(like)
    }

    fun addAllLike(userIds: List<Long>){
        userIds.forEach { addLike(it) }
    }

    fun increaseViewCnt(viewsCnt: Int){
        this.viewsCnt += viewsCnt
    }

    override fun toString(): String {
        return "Store(id=$id, name=$name, category='$category', address='$address', latitude=$latitude, longitude=$longitude, viewsCnt=$viewsCnt, likesCnt=$likesCnt, uuid='$uuid')"
    }

}