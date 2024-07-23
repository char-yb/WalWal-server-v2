package com.depromeet.walwal.domain.mission.application

import com.depromeet.walwal.domain.mission.dao.MissionRepository
import com.depromeet.walwal.domain.mission.domain.Mission
import com.depromeet.walwal.domain.mission.dto.request.MissionCreateRequest
import com.depromeet.walwal.domain.mission.dto.request.MissionUpdateRequest
import com.depromeet.walwal.domain.mission.dto.response.MissionCreateResponse
import com.depromeet.walwal.domain.mission.dto.response.MissionFindOneResponse
import com.depromeet.walwal.domain.mission.dto.response.MissionUpdateResponse
import com.depromeet.walwal.global.error.exception.CustomException
import com.depromeet.walwal.global.error.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MissionService(
	private val missionRepository: MissionRepository,
) {
	fun createMission(missionCreateRequest: MissionCreateRequest): MissionCreateResponse {
		var mission =
			Mission(
				title = missionCreateRequest.title,
			)
		mission = missionRepository.save(mission)
		return MissionCreateResponse.from(mission)
	}

	@Transactional(readOnly = true)
	fun getMission(missionId: Long): MissionFindOneResponse {
		return missionRepository
			.findById(missionId)
			.map(MissionFindOneResponse::from)
			.orElseThrow { CustomException(ErrorCode.MISSION_NOT_FOUND) }
	}

	fun updateMission(
		missionId: Long,
		missionUpdateRequest: MissionUpdateRequest,
	): MissionUpdateResponse {
		val missionToUpdate: Mission =
			missionRepository
				.findById(missionId)
				.orElseThrow { CustomException(ErrorCode.MISSION_NOT_FOUND) }

		missionToUpdate.updateTitle(missionUpdateRequest.title)
		missionRepository.save(missionToUpdate)

		return MissionUpdateResponse.from(missionToUpdate)
	}

	fun deleteMission(missionId: Long) {
		missionRepository.deleteById(missionId)
	}
}
