package com.depromeet.walwal.domain.auth.dto.request

import io.swagger.v3.oas.annotations.media.Schema

data class SocialLoginRequest(
	@Schema(description = "apple auth code", example = "authorization_code") var token: String,
) {
	init {
		token = token
	}
}
