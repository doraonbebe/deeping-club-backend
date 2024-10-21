package com.category.ranking.rankingservice.mapDomain.config


import org.springframework.context.annotation.Configuration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

//@Configuration
//@EnableElasticsearchRepositories
////@ConfigurationProperties(prefix = "spring.elasticsearch")
//class ElasticSearchConfig: ElasticsearchConfiguration(
//
//) {
//
//    override fun clientConfiguration(): ClientConfiguration {
//        return ClientConfiguration.builder()
//            .connectedTo("localhost:9200")
//            .build()
//    }
//
//}