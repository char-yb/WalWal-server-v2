package com.depromeet.walwal.domain.missionRecord.api

import com.depromeet.walwal.domain.missionRecord.application.MissionRecordService
import com.depromeet.walwal.domain.missionRecord.dto.request.MissionRecordCreateRequest
import com.depromeet.walwal.domain.missionRecord.dto.response.MissionRecordCreateResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "3. 미션 기록", description = "미션 기록 관련 API입니다.")
@RestController
@RequestMapping("/records")
class MissionRecordController(
	private val missionRecordService: MissionRecordService,
) {
	@Operation(summary = "미션 기록 저장", description = "미션 완료 후 기록을 저장한다.")
	@PostMapping
	fun completeMission(
		@RequestBody request: @Valid MissionRecordCreateRequest,
	): MissionRecordCreateResponse {
		return missionRecordService.completeMission(request)
	}

	@Operation(summary = "미션 기록 삭제", description = "미션 기록을 삭제한다.")
	@DeleteMapping("/{recordId}")
	fun deleteMissionRecord(
		@PathVariable recordId: Long,
	) {
		missionRecordService.deleteMissionRecord(recordId)
	}
}
