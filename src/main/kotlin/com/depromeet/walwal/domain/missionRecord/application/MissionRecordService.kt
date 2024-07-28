package com.depromeet.walwal.domain.missionRecord.application

import com.depromeet.walwal.domain.mission.dao.MissionRepository
import com.depromeet.walwal.domain.mission.domain.Mission
import com.depromeet.walwal.domain.missionRecord.dao.MissionRecordRepository
import com.depromeet.walwal.domain.missionRecord.domain.MissionRecord
import com.depromeet.walwal.domain.missionRecord.domain.MissionStatus
import com.depromeet.walwal.domain.missionRecord.dto.request.MissionRecordCreateRequest
import com.depromeet.walwal.domain.missionRecord.dto.response.MissionRecordCreateResponse
import com.depromeet.walwal.global.error.exception.CustomException
import com.depromeet.walwal.global.error.exception.ErrorCode
import com.depromeet.walwal.global.util.MemberUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MissionRecordService(
	private val missionRepository: MissionRepository,
	private val missionRecordRepository: MissionRecordRepository,
	private val memberUtil: MemberUtil,
) {
	fun completeMission(request: MissionRecordCreateRequest): MissionRecordCreateResponse {
		val mission = findMissionById(request.missionId)

		val member = memberUtil.currentMember

		val missionRecord =
			MissionRecord.createMissionRecord(
				member = member,
				mission = mission,
				imageUrl = "",
				status = MissionStatus.COMPLETED,
			)

		val createRecord = missionRecordRepository.save(missionRecord)
		return MissionRecordCreateResponse.from(
			createRecord.id!!,
			createRecord.mission.title,
		)
	}

	fun deleteMissionRecord(recordId: Long) {
		val missionRecord: MissionRecord =
			missionRecordRepository
				.findById(recordId)
				.orElseThrow { CustomException(ErrorCode.MISSION_RECORD_NOT_FOUND) }

		missionRecordRepository.delete(missionRecord)
	}

	// 단일 미션 조회 메서드
	private fun findMissionById(missionId: Long): Mission {
		return missionRepository
			.findById(missionId)
			.orElseThrow { CustomException(ErrorCode.MISSION_NOT_FOUND) }
	}
}
