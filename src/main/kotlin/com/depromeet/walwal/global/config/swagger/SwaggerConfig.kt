package com.depromeet.stonebed.global.config.swagger

import com.depromeet.walwal.global.common.constants.UrlConstants
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.core.jackson.ModelResolver
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig() {
	@Value("\${api.version}")
	private val apiVersion: String? = null

	// OpenAPI Bean 설정
	@Bean
	fun openAPI(): OpenAPI {
		return OpenAPI()
			.servers(swaggerServers())
			.addSecurityItem(securityRequirement())
			.components(authSetting())
			.info(
				Info()
					.title(SWAGGER_API_TITLE)
					.version(apiVersion)
					.description(SWAGGER_API_DESCRIPTION),
			)
	}

	private fun swaggerServers(): List<Server> {
		val localServer =
			Server().url(UrlConstants.LOCAL_SERVER_URL.value).description(SWAGGER_API_DESCRIPTION)
		val devServer =
			Server().url(UrlConstants.DEV_SERVER_URL.value).description(SWAGGER_API_DESCRIPTION)
		return listOf(localServer, devServer)
	}

	private fun authSetting(): Components {
		return Components()
			.addSecuritySchemes(
				"accessToken",
				SecurityScheme()
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT")
					.`in`(SecurityScheme.In.HEADER)
					.name("Authorization"),
			)
	}

	// GroupedOpenApi Bean 설정
	@Bean
	fun groupedOpenApi(): GroupedOpenApi {
		return GroupedOpenApi.builder()
			.group("WalWal API")
			.packagesToScan(PACKAGES_TO_SCAN)
			.build()
	}

	private fun securityRequirement(): SecurityRequirement {
		val securityRequirement = SecurityRequirement()
		securityRequirement.addList("accessToken")
		return securityRequirement
	}

	@Bean
	fun modelResolver(objectMapper: ObjectMapper): ModelResolver {
		return ModelResolver(objectMapper)
	}

	companion object {
		private const val PACKAGES_TO_SCAN = "com.depromeet.stonebed"
		private const val SWAGGER_API_TITLE = "WalWal 프로젝트 API 문서"
		private const val SWAGGER_API_DESCRIPTION = "WalWal 프로젝트 API 문서입니다."
	}
}
