package com.depromeet.walwal.infra.properties

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(
	RedisProperties::class,
	AppleProperties::class,
	JwtProperties::class,
	S3Properties::class,
	SwaggerProperties::class,
)
@Configuration
class PropertiesConfig
