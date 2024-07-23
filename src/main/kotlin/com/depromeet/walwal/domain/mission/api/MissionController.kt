package com.depromeet.walwal.domain.mission.api

import com.depromeet.walwal.domain.mission.application.MissionService
import com.depromeet.walwal.domain.mission.dto.request.MissionCreateRequest
import com.depromeet.walwal.domain.mission.dto.request.MissionUpdateRequest
import com.depromeet.walwal.domain.mission.dto.response.MissionCreateResponse
import com.depromeet.walwal.domain.mission.dto.response.MissionFindOneResponse
import com.depromeet.walwal.domain.mission.dto.response.MissionUpdateResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/missions")
@Tag(name = "Mission API", description = "미션 API")
class MissionController(
	private val missionService: MissionService,
) {
	@PostMapping
	fun createMission(
		@RequestBody @Valid missionCreateRequest: MissionCreateRequest,
	): MissionCreateResponse {
		return missionService.createMission(missionCreateRequest)
	}

	@GetMapping("/{missionId}")
	fun getMission(
		@PathVariable missionId: Long,
	): MissionFindOneResponse {
		return missionService.getMission(missionId)
	}

	@PatchMapping("/{missionId}")
	fun updateMission(
		@PathVariable missionId: Long,
		@RequestBody missionUpdateRequest: @Valid MissionUpdateRequest,
	): MissionUpdateResponse {
		return missionService.updateMission(missionId, missionUpdateRequest)
	}

	@DeleteMapping("/{missionId}")
	fun deleteMission(
		@PathVariable missionId: Long,
	) {
		missionService.deleteMission(missionId)
	}
}
