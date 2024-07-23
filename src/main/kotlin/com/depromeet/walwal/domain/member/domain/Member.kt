package com.depromeet.walwal.domain.member.domain

import com.depromeet.walwal.domain.common.BaseTimeEntity
import com.depromeet.walwal.domain.missionRecord.domain.MissionRecord
import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "member")
@Entity
class Member private constructor(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	var id: Long,
	@Embedded
	val profile: Profile = Profile.createProfile("", ""),
	@Embedded
	val oauthInfo: OauthInfo?,
	@Enumerated(EnumType.STRING)
	val status: MemberStatus?,
	@Enumerated(EnumType.STRING)
	val role: MemberRole?,
	val lastLoginAt: LocalDateTime?,
	@OneToMany(mappedBy = "member", cascade = [CascadeType.ALL])
	val missionRecords: List<MissionRecord> = listOf(),
) : BaseTimeEntity()
