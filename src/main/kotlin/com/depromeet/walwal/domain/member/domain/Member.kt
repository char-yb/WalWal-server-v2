package com.depromeet.walwal.domain.member.domain

import com.depromeet.walwal.domain.common.BaseTimeEntity
import com.depromeet.walwal.domain.missionRecord.domain.MissionRecord
import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "member")
@Entity
class Member : BaseTimeEntity() {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	var id: Long? = null

	@Embedded
	val profile = Profile.createProfile("", "")

	@Embedded
	val oauthInfo: OauthInfo? = null

	@Enumerated(EnumType.STRING)
	val status: MemberStatus? = null

	@Enumerated(EnumType.STRING)
	val role: MemberRole? = null

	val lastLoginAt: LocalDateTime? = null

	@OneToMany(mappedBy = "member", cascade = [CascadeType.ALL])
	val missionRecords: List<MissionRecord> = listOf()
}
