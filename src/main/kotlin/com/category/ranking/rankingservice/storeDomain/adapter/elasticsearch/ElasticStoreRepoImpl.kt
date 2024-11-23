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

    override fun findStoresByRadiusAndCategory(
        lat: Double,
        lon: Double,
        radius: Int,
        category: String
    ): List<StoreResponse> {
        val query = findStoreByRadiusAndCategoryQuery(lat, lon, radius, category)
        return searchStores(query)
    }

    override fun findStoresByRadiusLimit(lat: Double, lon: Double, radius: Int, limit: Int): List<StoreResponse> {
        val query = findStoreByRadiusLimitQuery(lat, lon, radius, limit)
        return searchStores(query)
    }

    private fun searchStores(query: String): List<StoreResponse> {
        val request = Request(HttpMethod.GET.name(), "/$indexName/_search").apply {
            entity = StringEntity(query, ContentType.APPLICATION_JSON)
        }

        val response: Response = restClient.performRequest(request)
        val responseBody = response.entity.content.bufferedReader().use { it.readText() }

        val searchResponse: ElasticsearchResponse<StoreResponse> = objectMapper.readValue(
            responseBody,
            object : TypeReference<ElasticsearchResponse<StoreResponse>>() {}
        )

        return searchResponse.hits.hits.map {hit ->
            val sort = (hit.sort?.firstOrNull() as Double).toInt()
            hit._source.copy(distance = sort)
        }
    }

    private fun findStoreByRadiusLimitQuery(lat: Double, lon: Double, radius: Int, size: Int): String {
        return """
        {
            "size": $size,
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
            },
            "sort": [
                {
                  "_geo_distance": {
                        "location": {
                          "lat": ${lat},
                          "lon": ${lon}
                    },
                    "order": "asc",
                    "unit": "m"
                }
            }
        ]
    }
    """.trimIndent()
    }

    private fun findStoreByRadiusAndCategoryQuery(lat: Double, lon: Double, radius: Int, category: String?): String {
        val categoryClause = if (category.isNullOrBlank()) "" else "{ \"term\": { \"category\": \"$category\" } },"
        return """
        {
            "query": {
                "bool": {
                    "filter": [
                        $categoryClause
                        {
                            "geo_distance": {
                                "distance": "${radius}m",
                                "location": {
                                    "lat": $lat,
                                    "lon": $lon
                            }
                        }
                    }
                ]
            }
        },
        "sort": [
            {
              "_geo_distance": {
                    "location": {
                      "lat": ${lat},
                      "lon": ${lon}
                },
                "order": "asc",
                "unit": "m"
          }
        }
      ]
    }
    """.trimIndent()
    }

}


