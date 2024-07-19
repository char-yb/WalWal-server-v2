package com.depromeet.walwal.domain.missionRecord.domain

import com.depromeet.walwal.domain.common.BaseTimeEntity
import com.depromeet.walwal.domain.member.domain.Member
import com.depromeet.walwal.domain.mission.domain.Mission
import jakarta.persistence.*

@Entity
class MissionRecord(
	member: Member,
	mission: Mission,
	@Column(name = "image_url") private var imageUrl: String,
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false) private var status: MissionStatus,
) : BaseTimeEntity() {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "record_id")
	private var id: Long? = null

	@ManyToOne
	@JoinColumn(name = "mission_id", nullable = false)
	private val mission: Mission = mission

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private val member: Member = member

	fun updateImageUrl(imageUrl: String) {
		this.imageUrl = imageUrl
	}
}
