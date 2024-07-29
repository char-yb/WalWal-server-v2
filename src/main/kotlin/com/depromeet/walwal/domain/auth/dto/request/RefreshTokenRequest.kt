package com.depromeet.walwal.domain.auth.dto.request

import io.swagger.v3.oas.annotations.media.Schema

data class RefreshTokenRequest(
	@Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIU")
	val refreshToken: String,
)
