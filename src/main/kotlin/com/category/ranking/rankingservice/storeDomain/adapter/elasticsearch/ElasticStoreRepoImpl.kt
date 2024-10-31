package com.category.ranking.rankingservice.storeDomain.adapter.elasticsearch

import co.elastic.clients.elasticsearch.ElasticsearchClient
import com.category.ranking.rankingservice.storeDomain.adapter.api.out.ElasticsearchResponse
import com.category.ranking.rankingservice.storeDomain.adapter.api.out.StoreResponse
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
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

    override fun findStoresByLocationAndDistance(lat: Double, lon: Double, radius: Int): List<StoreResponse> {

        val query = findStoreByLocationAndDistanceQuery(lat, lon, radius)

        val request = Request(HttpMethod.GET.name(), "/$indexName/_search")
        request.addParameter("pretty", "true")
        request.entity = org.apache.http.entity.StringEntity(query, org.apache.http.entity.ContentType.APPLICATION_JSON)

        val response: Response = restClient.performRequest(request)
        val responseBody = response.entity.content.bufferedReader().use { it.readText() }

        val searchResponse: ElasticsearchResponse<StoreResponse> = objectMapper.readValue(
            responseBody,
            object : TypeReference<ElasticsearchResponse<StoreResponse>>() {}
        )

        return searchResponse.hits.hits.map { it._source }

    }

    private fun findStoreByLocationAndDistanceQuery(lat: Double, lon: Double, radius: Int): String {
        return """
        {
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


