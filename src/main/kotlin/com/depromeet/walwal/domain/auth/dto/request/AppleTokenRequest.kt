package com.depromeet.walwal.domain.auth.dto.request

data class AppleTokenRequest( // 외부 통신 시 snake_case로 요청 및 응답
	val code: String,
	val client_id: String,
	val client_secret: String,
	val grant_type: String,
) {
	companion object {
		fun of(
			code: String,
			clientId: String,
			clientSecret: String,
			grantType: String,
		): AppleTokenRequest {
			return AppleTokenRequest(code, clientId, clientSecret, grantType)
		}
	}
}
