package com.category.ranking.rankingservice.storeDomain.esDomain

import jakarta.persistence.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.annotations.GeoPointField
import org.springframework.data.elasticsearch.core.geo.GeoPoint

@Document(indexName = "store")
class ElasticStore (

    @Id
    val id: String? = null,

    @Field(type = FieldType.Text)
    val name: String ?= "",

    @Field(type = FieldType.Keyword)
    val category: String? = "",

    @GeoPointField
    val location: GeoPoint? = null,

    @Field(type = FieldType.Text)
    val address: String? = "",

)