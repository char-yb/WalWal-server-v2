package com.depromeet.walwal.domain.auth.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@JvmRecord
data class AuthTokenResponse(
	@Schema(description = "엑세스 토큰", example = "accessToken")
	val accessToken: String,
	@Schema(description = "리프레시 토큰", example = "refreshToken")
	val refreshToken: String,
	@Schema(description = "임시 토큰 여부", example = "false")
	val isTemporaryToken: Boolean,
) {
	companion object {
		fun of(
			tokenPairResponse: TokenPairResponse,
			isTemporaryToken: Boolean,
		): AuthTokenResponse {
			return AuthTokenResponse(
				tokenPairResponse.accessToken,
				tokenPairResponse.refreshToken,
				isTemporaryToken,
			)
		}
	}
}
