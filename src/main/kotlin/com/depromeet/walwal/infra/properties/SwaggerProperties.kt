package com.depromeet.walwal.infra.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "swagger")
data class SwaggerProperties(val version: String, val user: String, val password: String)
