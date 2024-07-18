package com.depromeet.walwal.domain.member.domain

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Embeddable

@Embeddable
class OauthInfo(
	@field:Schema(
		description = "소셜 ID",
		example = "123487892",
	) private val oauthId: String,
	@field:Schema(
		description = "소셜 제공자",
		example = "KAKAO",
	) private val oauthProvider: String,
	@field:Schema(
		description = "소셜 이메일",
		example = "test@gmail.com",
	) private val oauthEmail: String,
) {
	companion object {
		fun createOauthInfo(
			oauthId: String,
			oauthProvider: String,
			oauthEmail: String,
		): OauthInfo {
			return OauthInfo(
				oauthId,
				oauthProvider,
				oauthEmail,
			)
		}
	}
}
