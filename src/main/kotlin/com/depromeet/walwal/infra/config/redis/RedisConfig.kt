package com.depromeet.walwal.infra.config.redis

import com.depromeet.walwal.infra.properties.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import java.time.Duration

@EnableRedisRepositories
@Configuration
class RedisConfig(
	private val redisProperties: RedisProperties,
) {
	@Bean
	fun redisConnectionFactory(): RedisConnectionFactory {
		val redisConfig =
			RedisStandaloneConfiguration(redisProperties.host, redisProperties.port)
		if (redisProperties.password.isNotBlank()) {
			redisConfig.setPassword(redisProperties.password)
		}
		val clientConfig: LettuceClientConfiguration =
			LettuceClientConfiguration.builder()
				.commandTimeout(Duration.ofSeconds(1))
				.shutdownTimeout(Duration.ZERO)
				.build()
		return LettuceConnectionFactory(redisConfig, clientConfig)
	}
}
