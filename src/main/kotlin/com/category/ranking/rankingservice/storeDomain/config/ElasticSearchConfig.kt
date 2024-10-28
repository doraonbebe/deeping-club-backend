package com.category.ranking.rankingservice.storeDomain.config



import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestClientBuilder

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ElasticSearchConfig {
    // root , 123456
    @Bean(name = ["customElasticsearchClient"])
    fun elasticsearchClient(): RestClient {
        return RestClient.builder(HttpHost("", 9200, "http"))
            .setRequestConfigCallback(RestClientBuilder.RequestConfigCallback { requestConfig ->
                requestConfig.setConnectTimeout(5000)
                requestConfig.setSocketTimeout(60000)
            })
            .build()
    }
}