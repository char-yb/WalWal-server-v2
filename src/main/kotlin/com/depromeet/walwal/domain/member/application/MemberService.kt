package com.depromeet.walwal.domain.member.application

import com.depromeet.walwal.domain.auth.domain.OAuthProvider
import com.depromeet.walwal.domain.member.dao.MemberRepository
import com.depromeet.walwal.domain.member.domain.Member
import com.depromeet.walwal.domain.member.domain.Profile
import com.depromeet.walwal.domain.member.dto.request.CreateMemberRequest
import com.depromeet.walwal.global.util.MemberUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class MemberService(
	private val memberRepository: MemberRepository,
	private val memberUtil: MemberUtil,
) {
	@Transactional(readOnly = true)
	fun findMemberInfo(): Member {
		return memberUtil.currentMember
	}

	@Transactional(readOnly = true)
	fun getMemberByOauthId(
		oAuthProvider: OAuthProvider,
		oauthId: String?,
	): Optional<Member> {
		return memberRepository.findByOauthInfoOauthProviderAndOauthInfoOauthId(
			oAuthProvider.value,
			oauthId,
		)
	}

	fun setMemberRegister(
		member: Member,
		request: CreateMemberRequest,
	): Member {
		member.updateProfile(Profile.createProfile(request.nickname, request.profileImageUrl))
		member.updateRaisePet(request.raisePet)
		member.updateMarketingAgreement(request.marketingAgreement)
		return member
	}

	fun socialSignUp(
		oAuthProvider: OAuthProvider,
		oauthId: String,
		email: String,
	): Member {
		val member: Member = Member.createOAuthMember(oAuthProvider, oauthId, email)
		return memberRepository.save(member)
	}
}
