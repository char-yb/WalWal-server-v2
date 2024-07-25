package com.depromeet.walwal.domain.missionRecord.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

@JvmRecord
data class MissionRecordCreateRequest(
	@Schema(
		description = "미션 아이디",
		defaultValue = "1",
	) val missionId: @NotNull Long,
)
