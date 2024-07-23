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
	var id: Long,
	@ManyToOne
	@JoinColumn(name = "mission_id", nullable = false)
	val mission: Mission,
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	val member: Member,
	@Column(name = "image_url") private var imageUrl: String,
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false) private var status: MissionStatus,
) : BaseTimeEntity() {
	fun updateImageUrl(imageUrl: String) {
		this.imageUrl = imageUrl
	}
}
