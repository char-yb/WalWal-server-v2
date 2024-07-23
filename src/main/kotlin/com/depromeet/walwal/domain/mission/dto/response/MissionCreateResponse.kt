package com.depromeet.walwal.domain.mission.dto.response

import com.depromeet.walwal.domain.mission.domain.Mission
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class MissionCreateResponse(
	@Schema(
		description = "미션 ID",
		example = "1",
	) val id: @NotBlank Long?,
	@Schema(
		description = "미션 제목",
		example = "산책하기",
	) val title:
		@NotBlank(message = "Title cannot be blank")
		String?,
) {
	companion object {
		fun from(mission: Mission): MissionCreateResponse {
			return MissionCreateResponse(
				mission.id,
				mission.title,
			)
		}
	}
}
