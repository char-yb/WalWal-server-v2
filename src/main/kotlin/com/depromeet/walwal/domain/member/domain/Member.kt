package com.depromeet.walwal.domain.member.domain

import com.depromeet.walwal.domain.auth.domain.OAuthProvider
import com.depromeet.walwal.domain.common.BaseEntity
import com.depromeet.walwal.domain.missionRecord.domain.MissionRecord
import jakarta.persistence.*
import java.time.LocalDateTime
import kotlin.reflect.full.isSubclassOf

@Table(name = "member")
@Entity
class Member private constructor(
	@Embedded
	var profile: Profile = Profile.createProfile("", ""),
	@Embedded
	val oauthInfo: OauthInfo?,
	@Enumerated(EnumType.STRING)
	val status: MemberStatus?,
	@Enumerated(EnumType.STRING)
	val role: MemberRole?,
	var lastLoginAt: LocalDateTime?,
	@Enumerated(EnumType.STRING)
	var raisePet: RaisePet?,
	@Enumerated(EnumType.STRING)
	var marketingAgree: MarketingAgreement?,
	@OneToMany(mappedBy = "member", cascade = [CascadeType.ALL])
	val missionRecords: List<MissionRecord> = listOf(),
) : BaseEntity() {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	var id: Long? = null

	// 기본 생성자
	constructor() : this(
		profile = Profile.createProfile("", ""),
		oauthInfo = null,
		status = null,
		role = null,
		lastLoginAt = null,
		raisePet = null,
		marketingAgree = null,
		missionRecords = listOf(),
	)

	// Proxy 객체 고려하여 equals Override, https://zins.tistory.com/19
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Member) return false
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
		fun createMember(
			profile: Profile,
			oauthInfo: OauthInfo,
			status: MemberStatus?,
			role: MemberRole?,
			raisePet: RaisePet?,
			marketingAgree: MarketingAgreement?,
		): Member {
			return Member(
				profile = profile,
				oauthInfo = oauthInfo,
				status = status,
				role = role,
				lastLoginAt = null,
				missionRecords = listOf(),
				raisePet = raisePet,
				marketingAgree = marketingAgree,
			)
		}

		fun createOAuthMember(
			oAuthProvider: OAuthProvider,
			oauthId: String,
			email: String,
		): Member {
			val oauthInfo = OauthInfo.createOauthInfo(oauthId, oAuthProvider.value!!, email)

			return createMember(
				Profile.createProfile("", ""),
				oauthInfo,
				MemberStatus.NORMAL,
				MemberRole.USER,
				null,
				MarketingAgreement.DISAGREED,
			)
		}
	}

	fun updateLastLoginAt() {
		this.lastLoginAt = LocalDateTime.now()
	}

	fun updateRaisePet(raisePet: RaisePet?) {
		this.raisePet = raisePet
	}

	fun updateMarketingAgreement(marketingAgree: MarketingAgreement?) {
		this.marketingAgree = marketingAgree
	}

	fun updateProfile(profile: Profile?) {
		this.profile = profile!!
	}
}
