package com.depromeet.walwal.domain.auth.dto.response

data class AppleTokenResponse( // 외부 통신 시 snake_case로 요청 및 응답
	val access_token: String,
	val expires_in: Long,
	val id_token: String,
	val refresh_token: String,
	val token_type: String,
) {
	companion object {
		fun of(
			accessToken: String,
			expiresIn: Long,
			idToken: String,
			refreshToken: String,
			tokenType: String,
		): AppleTokenResponse {
			return AppleTokenResponse(accessToken, expiresIn, idToken, refreshToken, tokenType)
		}
	}
}
