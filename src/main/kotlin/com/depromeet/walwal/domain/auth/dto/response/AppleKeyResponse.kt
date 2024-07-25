package com.depromeet.walwal.domain.auth.dto.response

data class AppleKeyResponse(
	val kty: String,
	val kid: String,
	val use: String,
	val alg: String,
	val n: String,
	val e: String,
)
