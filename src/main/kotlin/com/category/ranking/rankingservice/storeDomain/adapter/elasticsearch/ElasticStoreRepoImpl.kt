package com.category.ranking.rankingservice.storeDomain.adapter.elasticsearch

import co.elastic.clients.elasticsearch.ElasticsearchClient
import com.category.ranking.rankingservice.storeDomain.adapter.api.out.elasticsearch.ElasticsearchResponse
import com.category.ranking.rankingservice.storeDomain.adapter.api.out.elasticsearch.StoreResponse
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.elasticsearch.client.Request
import org.elasticsearch.client.Response
import org.elasticsearch.client.RestClient
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Repository


@Repository
class ElasticStoreRepoImpl(
    private val elasticsearchClient: ElasticsearchClient,
    private val restClient: RestClient,
    private val objectMapper: ObjectMapper
) : ElasticSearchCustomRepository {

    val indexName = "store"

    override fun findStoresByRadius(lat: Double, lon: Double, radius: Int): List<StoreResponse> {
        return searchStores(lat, lon, radius, null)
    }

    override fun findStoresByRadiusLimit(lat: Double, lon: Double, radius: Int, limit: Int): List<StoreResponse> {
        return searchStores(lat, lon, radius, limit)
    }

    private fun searchStores(lat: Double, lon: Double, radius: Int, limit: Int?): List<StoreResponse> {
        val query = findStoreByLocationAndDistanceQuery(lat, lon, radius, limit)

        val request = Request(HttpMethod.GET.name(), "/$indexName/_search").apply {
            entity = StringEntity(query, ContentType.APPLICATION_JSON)
        }

        val response: Response = restClient.performRequest(request)
        val responseBody = response.entity.content.bufferedReader().use { it.readText() }

        val searchResponse: ElasticsearchResponse<StoreResponse> = objectMapper.readValue(
            responseBody,
            object : TypeReference<ElasticsearchResponse<StoreResponse>>() {}
        )

        return searchResponse.hits.hits.map { it._source }
    }

    private fun findStoreByLocationAndDistanceQuery(lat: Double, lon: Double, radius: Int, size: Int?): String {
        val sizeClause = size?.let { "\"size\": $it," } ?: ""

        return """
        {
            $sizeClause
            "query": {
                "bool": {
                    "filter": {
                        "geo_distance": {
                            "distance": "${radius}m",
                            "location": {
                                "lat": $lat,
                                "lon": $lon
                            }
                        }
                    }
                }
            }
        }
    """.trimIndent()
    }

}


