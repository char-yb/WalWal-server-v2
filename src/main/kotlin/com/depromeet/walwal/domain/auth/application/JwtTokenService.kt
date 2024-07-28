package com.depromeet.walwal.domain.auth.application

import com.depromeet.walwal.domain.auth.dao.RefreshTokenRepository
import com.depromeet.walwal.domain.auth.domain.RefreshToken
import com.depromeet.walwal.domain.auth.dto.AccessTokenDto
import com.depromeet.walwal.domain.auth.dto.RefreshTokenDto
import com.depromeet.walwal.domain.auth.dto.response.TokenPairResponse
import com.depromeet.walwal.domain.member.domain.Member
import com.depromeet.walwal.domain.member.domain.MemberRole
import com.depromeet.walwal.global.common.constants.SecurityConstants.TOKEN_ROLE_NAME
import com.depromeet.walwal.global.util.JwtUtil
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtTokenService(
	private val jwtUtil: JwtUtil,
	private val refreshTokenRepository: RefreshTokenRepository,
) {
	fun generateTokenPair(
		memberId: Long,
		memberRole: MemberRole,
	): TokenPairResponse {
		val accessToken = createAccessToken(memberId, memberRole)
		val refreshToken = createRefreshToken(memberId)
		return TokenPairResponse.of(accessToken, refreshToken)
	}

	fun generateTemporaryTokenPair(temporaryMember: Member): TokenPairResponse {
		val accessToken = createAccessToken(temporaryMember.id, MemberRole.TEMPORARY)
		val refreshToken = createRefreshToken(temporaryMember.id)
		return TokenPairResponse.of(accessToken, refreshToken)
	}

	private fun createAccessToken(
		memberId: Long,
		memberRole: MemberRole,
	): String {
		return jwtUtil.generateAccessToken(memberId, memberRole)
	}

	fun createAccessTokenDto(
		memberId: Long,
		memberRole: MemberRole,
	): AccessTokenDto {
		return jwtUtil.generateAccessTokenDto(memberId, memberRole)
	}

	private fun createRefreshToken(memberId: Long): String {
		val token: String = jwtUtil.generateRefreshToken(memberId)
		saveRefreshTokenToRedis(memberId, token, jwtUtil.refreshTokenExpirationTime)
		return token
	}

	fun createRefreshTokenDto(memberId: Long): RefreshTokenDto {
		val refreshTokenDto: RefreshTokenDto = jwtUtil.generateRefreshTokenDto(memberId)
		saveRefreshTokenToRedis(memberId, refreshTokenDto.tokenValue, refreshTokenDto.ttl)
		return refreshTokenDto
	}

	private fun saveRefreshTokenToRedis(
		memberId: Long,
		refreshTokenDto: String,
		ttl: Long,
	) {
		val refreshToken = RefreshToken(memberId, refreshTokenDto, ttl)
		refreshTokenRepository.save(refreshToken)
	}

	fun retrieveAccessToken(accessTokenValue: String): AccessTokenDto? {
		return try {
			jwtUtil.parseAccessToken(accessTokenValue)
		} catch (e: Exception) {
			null
		}
	}

	fun retrieveRefreshToken(refreshTokenValue: String): RefreshTokenDto? {
		val refreshTokenDto: RefreshTokenDto? = parseRefreshToken(refreshTokenValue)
		println("refreshTokenDto: $refreshTokenDto")
		if (refreshTokenDto == null) {
			return null
		}

		// 파싱된 DTO와 일치하는 토큰이 Redis에 저장되어 있는지 확인
		val refreshToken: Optional<RefreshToken?> = getRefreshTokenFromRedis(refreshTokenDto.memberId)

		// Redis에 토큰이 존재하고, 쿠키의 토큰과 값이 일치하면 DTO 반환
		if (refreshToken.isPresent) {
			return refreshTokenDto
		}

		// Redis에 토큰이 존재하지 않거나, 쿠키의 토큰과 값이 일치하지 않으면 null 반환
		return null
	}

	private fun getRefreshTokenFromRedis(memberId: Long): Optional<RefreshToken?> {
		return refreshTokenRepository.findById(memberId)
	}

	private fun parseRefreshToken(refreshTokenValue: String): RefreshTokenDto? {
		return try {
			jwtUtil.parseRefreshToken(refreshTokenValue)
		} catch (e: Exception) {
			null
		}
	}

	fun reissueAccessTokenIfExpired(accessTokenValue: String): AccessTokenDto? {
		// AT가 만료된 경우 AT 재발급, 만료되지 않은 경우 null 반환
		try {
			jwtUtil.parseAccessToken(accessTokenValue)
			return null
		} catch (e: ExpiredJwtException) {
			val memberId: Long = e.claims.subject.toLong()
			val memberRole: MemberRole =
				MemberRole.valueOf(e.claims.get(TOKEN_ROLE_NAME, String::class.java))
			return createAccessTokenDto(memberId, memberRole)
		}
	}
}
