package com.depromeet.walwal.global.util

import com.depromeet.walwal.domain.member.dao.MemberRepository
import com.depromeet.walwal.domain.member.domain.Member
import com.depromeet.walwal.global.error.exception.CustomException
import com.depromeet.walwal.global.error.exception.ErrorCode
import org.springframework.stereotype.Component

@Component
class MemberUtil(
	private val securityUtil: SecurityUtil,
	private val memberRepository: MemberRepository,
) {

	val currentMember: Member
		get() =
			memberRepository
				.findById(securityUtil.currentMemberId)
				.orElseThrow { CustomException(ErrorCode.MEMBER_NOT_FOUND) }

	fun getMemberByMemberId(memberId: Long): Member {
		return memberRepository
			.findById(memberId)
			.orElseThrow { CustomException(ErrorCode.MEMBER_NOT_FOUND) }
	}

	val memberRole: String
		get() = securityUtil.currentMemberRole

	val memberProvider: String
		get() = currentMember.oauthInfo?.oauthProvider ?: ""
}
