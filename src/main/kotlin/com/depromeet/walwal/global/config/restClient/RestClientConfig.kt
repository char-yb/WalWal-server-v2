package com.depromeet.walwal.global.config.restClient

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import java.time.Duration

@Configuration
class RestClientConfig {
	@Bean
	fun restClient(): RestClient {
		val restTemplate =
			RestTemplateBuilder()
				.setConnectTimeout(Duration.ofSeconds(10))
				.setReadTimeout(Duration.ofSeconds(5))
				.build()

		return RestClient.create(restTemplate)
	}
}
