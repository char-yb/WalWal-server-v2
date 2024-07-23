package com.depromeet.walwal.domain.mission.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class MissionUpdateRequest(
	@Schema(
		description = "미션 제목",
		example = "산책하기",
	) val title:
		@NotBlank(message = "Title cannot be blank")
		String?,
)
