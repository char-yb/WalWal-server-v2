package com.depromeet.walwal.domain.member.dto.request

import com.depromeet.walwal.domain.member.domain.RaisePet
import io.swagger.v3.oas.annotations.media.Schema

data class CreateMemberRequest(
	@Schema(description = "닉네임", example = "walwal")
	val nickname: String,
	@Schema(description = "반려동물", example = "DOG")
	val raisePet: RaisePet,
)
