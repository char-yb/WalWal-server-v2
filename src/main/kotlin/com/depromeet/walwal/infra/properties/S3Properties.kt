package com.depromeet.walwal.infra.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "storage")
data class S3Properties(
	val accessKey: String,
	val secretKey: String,
	val region: String,
	val bucket: String,
	val endpoint: String,
)
