package com.depromeet.walwal.global.config.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
	@Value("\${api.version}")
	private val apiVersion: String? = null

	// OpenAPI Bean 설정
	@Bean
	fun openAPI(): OpenAPI {
		return OpenAPI()
			.info(
				Info()
					.title("WalWal 프로젝트 API v2 문서화")
					.version(apiVersion)
					.description("WalWal 프로젝트의 Swagger UI 화면입니다."),
			)
	}

	// GroupedOpenApi Bean 설정
	@Bean
	fun groupedOpenApi(): GroupedOpenApi {
		return GroupedOpenApi.builder()
			.group("WalWal API v2")
			.packagesToScan(PACKAGES_TO_SCAN)
			.build()
	}

	companion object {
		private const val PACKAGES_TO_SCAN = "com.depromeet.walwal"
	}
}
