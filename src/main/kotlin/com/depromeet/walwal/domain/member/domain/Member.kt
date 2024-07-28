package com.depromeet.walwal.domain.member.domain

import com.depromeet.walwal.domain.auth.domain.OAuthProvider
import com.depromeet.walwal.domain.common.BaseEntity
import com.depromeet.walwal.domain.missionRecord.domain.MissionRecord
import jakarta.persistence.*
import java.time.LocalDateTime

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
