package com.depromeet.walwal.domain.member.dto.request

import com.depromeet.walwal.domain.member.domain.MarketingAgreement
import com.depromeet.walwal.domain.member.domain.RaisePet
import io.swagger.v3.oas.annotations.media.Schema

data class CreateMemberRequest(
	@Schema(description = "닉네임", example = "walwal")
	val nickname: String,
	@Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
	val profileImageUrl: String,
	@Schema(description = "반려동물", example = "DOG")
	val raisePet: RaisePet,
	@Schema(description = "이메일", example = "test@test.com")
	val email: String,
	@Schema(description = "마케팅 수신동의 여부", example = "AGREED")
	val marketingAgreement: MarketingAgreement,
)
