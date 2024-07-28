package com.depromeet.walwal.domain.missionRecord.domain

import com.depromeet.walwal.domain.common.BaseEntity
import com.depromeet.walwal.domain.member.domain.Member
import com.depromeet.walwal.domain.mission.domain.Mission
import jakarta.persistence.*
import kotlin.reflect.full.isSubclassOf

@Entity
class MissionRecord private constructor(
	@Column(name = "image_url") var imageUrl: String = "",
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false) var status: MissionStatus,
	@ManyToOne
	@JoinColumn(name = "mission_id", nullable = false)
	val mission: Mission,
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	val member: Member,
) : BaseEntity() {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "record_id")
	var id: Long? = null

	// Proxy 객체 고려하여 equals Override, https://zins.tistory.com/19
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is MissionRecord) return false
		if (!compareClassesIncludeProxy(other)) return false
		if (id == null) return false
		if (id != other.id) return false
		return true
	}

	private fun compareClassesIncludeProxy(other: Any) =
		this::class.isSubclassOf(other::class) ||
			other::class.isSubclassOf(this::class)

	override fun hashCode(): Int = id?.hashCode() ?: 0

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
