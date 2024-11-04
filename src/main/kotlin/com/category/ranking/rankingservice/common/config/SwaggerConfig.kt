package com.category.ranking.rankingservice.common.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Deeping Club API")
                    .version("v1")
                    .description("맛집 지도 기반 서비스 API")
            )

    }

    @Bean
    fun storeApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("store")
            .pathsToMatch("/api/v1/stores/**")
            .build()
    }

    @Bean
    fun userApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("user")
            .pathsToMatch("/api/v1/users/**")
            .build()
    }

}