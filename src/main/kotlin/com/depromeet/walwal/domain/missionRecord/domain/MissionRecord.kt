package com.depromeet.walwal.domain.missionRecord.domain

import com.depromeet.walwal.domain.common.BaseTimeEntity
import com.depromeet.walwal.domain.member.domain.Member
import com.depromeet.walwal.domain.mission.domain.Mission
import jakarta.persistence.*

@Entity
class MissionRecord private constructor(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "record_id")
	var id: Long = 0,
	@Column(name = "image_url") var imageUrl: String = "",
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false) var status: MissionStatus,
	@ManyToOne
	@JoinColumn(name = "mission_id", nullable = false)
	val mission: Mission,
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	val member: Member,
) : BaseTimeEntity() {
	companion object {
		fun createMissionRecord(
			imageUrl: String,
			status: MissionStatus,
			mission: Mission,
			member: Member,
		): MissionRecord {
			with(MissionRecord) {
				return MissionRecord(
					imageUrl = imageUrl,
					status = status,
					mission = mission,
					member = member,
				)
			}
		}
	}

	fun updateImageUrl(imageUrl: String) {
		this.imageUrl = imageUrl
	}
}
