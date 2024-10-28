package com.category.ranking.rankingservice.storeDomain.adapter.elasticsearch

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.category.ranking.rankingservice.storeDomain.esDomain.ElasticStore
import org.springframework.stereotype.Repository
import java.io.IOException

@Repository
class ElasticStoreRepoImpl(
    private val elasticsearchClient: ElasticsearchClient
) : ElasticSearchCustomRepository {

    val indexName = "store"

    override fun getAllStore() {
        val matchAllQuery = Query.Builder()
            .matchAll { it }
            .build()

        val searchRequest = SearchRequest.Builder()
            .index(indexName)
            .query(matchAllQuery)
            .build()

        try {
            val response: SearchResponse<ElasticStore> = elasticsearchClient.search(searchRequest, ElasticStore::class.java)

            for (hit: Hit<ElasticStore> in response.hits().hits()) {
                val store = hit.source();
                println("name : ${store?.name}")
                println("category: ${store?.category}")
                println("location: ${store?.location}")
                println("address: ${store?.address}")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}