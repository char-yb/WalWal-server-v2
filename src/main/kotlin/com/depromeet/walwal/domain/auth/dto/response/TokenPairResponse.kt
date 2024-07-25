package com.depromeet.walwal.domain.auth.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class TokenPairResponse(
	@Schema(description = "액세스 토큰", defaultValue = "accessToken")
	val accessToken: String,
	@Schema(description = "리프레시 토큰", defaultValue = "refreshToken")
	val refreshToken: String,
) {
	companion object {
		fun of(
			accessToken: String,
			refreshToken: String,
		): TokenPairResponse {
			return TokenPairResponse(accessToken, refreshToken)
		}
	}
}
