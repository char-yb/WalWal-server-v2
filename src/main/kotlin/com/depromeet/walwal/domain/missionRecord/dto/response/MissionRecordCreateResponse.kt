package com.depromeet.walwal.domain.missionRecord.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class MissionRecordCreateResponse(
	@Schema(
		description = "미션 기록 ID",
		defaultValue = "1",
	) val recordId: Long,
	@Schema(description = "미션 제목") val missionTitle: String,
) {
	companion object {
		fun from(
			recordId: Long,
			missionTitle: String,
		): MissionRecordCreateResponse {
			return MissionRecordCreateResponse(recordId, missionTitle)
		}
	}
}
