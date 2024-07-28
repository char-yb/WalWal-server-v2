package com.depromeet.walwal.domain.auth.application

import com.depromeet.walwal.domain.auth.application.apple.AppleClient
import com.depromeet.walwal.domain.auth.domain.OAuthProvider
import com.depromeet.walwal.domain.auth.dto.RefreshTokenDto
import com.depromeet.walwal.domain.auth.dto.request.RefreshTokenRequest
import com.depromeet.walwal.domain.auth.dto.response.AuthTokenResponse
import com.depromeet.walwal.domain.auth.dto.response.SocialClientResponse
import com.depromeet.walwal.domain.auth.dto.response.TokenPairResponse
import com.depromeet.walwal.domain.member.application.MemberService
import com.depromeet.walwal.domain.member.domain.Member
import com.depromeet.walwal.domain.member.domain.MemberRole
import com.depromeet.walwal.domain.member.dto.request.CreateMemberRequest
import com.depromeet.walwal.global.error.exception.CustomException
import com.depromeet.walwal.global.error.exception.ErrorCode
import com.depromeet.walwal.global.util.MemberUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.InvalidParameterException
import java.util.*

@Service
@Transactional
class AuthService(
	private val appleClient: AppleClient,
	private val memberService: MemberService,
	private val jwtTokenService: JwtTokenService,
	private val memberUtil: MemberUtil,
) {
	fun authenticateFromProvider(
		provider: OAuthProvider,
		token: String,
	): SocialClientResponse {
        /* token
        1. apple의 경우 authorizationCode Value
        2. kakao의 경우 accessToken Value
         */
		return when (provider) {
			OAuthProvider.APPLE -> appleClient.authenticateFromApple(token)
			else -> throw InvalidParameterException()
		}
	}

	fun socialLogin(
		oAuthProvider: OAuthProvider,
		oauthId: String,
		email: String,
	): AuthTokenResponse {
		val memberOptional: Optional<Member> = memberService.getMemberByOauthId(oAuthProvider, oauthId)

		return memberOptional
			.map { member: Member ->
				// 사용자 로그인 토큰 생성
				val tokenPair: TokenPairResponse = getLoginResponse(member)
				member.updateLastLoginAt()
				AuthTokenResponse.of(tokenPair, false)
			}
			.orElseGet {
				// 회원가입이 안된 경우, 임시 회원가입 진행
				val newMember: Member =
					memberService.socialSignUp(oAuthProvider, oauthId, email)
				// 임시 토큰 발행
				val temporaryTokenPair: TokenPairResponse =
					jwtTokenService.generateTemporaryTokenPair(newMember)
				newMember.updateLastLoginAt()
				AuthTokenResponse.of(temporaryTokenPair, true)
			}
	}

	// 회원가입
	fun registerMember(request: CreateMemberRequest): AuthTokenResponse {
		val currentMember: Member = memberUtil.currentMember
		// 사용자 회원가입
		if (memberUtil.memberRole == MemberRole.TEMPORARY.value) {
			val member: Member = memberService.setMemberRegister(currentMember, request)
			// 새 토큰 생성
			val tokenPair: TokenPairResponse = getLoginResponse(member)
			return AuthTokenResponse.of(tokenPair, false)
		}
		throw CustomException(ErrorCode.ALREADY_EXISTS_MEMBER)
	}

	@Transactional(readOnly = true)
	fun reissueTokenPair(request: RefreshTokenRequest): AuthTokenResponse {
		// 리프레시 토큰을 이용해 새로운 액세스 토큰 발급
		val refreshTokenDto =
			jwtTokenService.retrieveRefreshToken(request.refreshToken)
		val refreshToken: RefreshTokenDto =
			jwtTokenService.createRefreshTokenDto(refreshTokenDto!!.memberId)

		val member: Member = memberUtil.getMemberByMemberId(refreshToken.memberId)

		val tokenPair: TokenPairResponse = getLoginResponse(member)
		return AuthTokenResponse.of(tokenPair, false)
	}

	private fun getLoginResponse(member: Member): TokenPairResponse = jwtTokenService.generateTokenPair(member.id!!, MemberRole.USER)
}
