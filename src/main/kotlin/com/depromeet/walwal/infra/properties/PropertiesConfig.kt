package com.depromeet.walwal.infra.properties

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(
	RedisProperties::class,
)
@Configuration
class PropertiesConfig
