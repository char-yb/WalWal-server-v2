package com.depromeet.walwal.global.filter

import com.depromeet.walwal.domain.auth.application.JwtTokenService
import com.depromeet.walwal.domain.auth.dto.AccessTokenDto
import com.depromeet.walwal.domain.auth.dto.RefreshTokenDto
import com.depromeet.walwal.domain.member.domain.MemberRole
import com.depromeet.walwal.global.common.constants.SecurityConstants.ACCESS_TOKEN_COOKIE_NAME
import com.depromeet.walwal.global.common.constants.SecurityConstants.REFRESH_TOKEN_COOKIE_NAME
import com.depromeet.walwal.global.common.constants.SecurityConstants.TOKEN_PREFIX
import com.depromeet.walwal.global.security.PrincipalDetails
import com.depromeet.walwal.global.util.CookieUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.WebUtils
import java.io.IOException
import java.util.*

class JwtAuthenticationFilter(
	private val jwtTokenService: JwtTokenService,
	private val cookieUtil: CookieUtil,
) : OncePerRequestFilter() {
	@Throws(ServletException::class, IOException::class)
	protected override fun doFilterInternal(
		request: HttpServletRequest,
		response: HttpServletResponse,
		filterChain: FilterChain,
	) {
		val accessTokenHeaderValue = extractAccessTokenFromHeader(request)
		val accessTokenValue = extractAccessTokenFromCookie(request)
		val refreshTokenValue = extractRefreshTokenFromCookie(request)

		// 헤더에 AT가 있으면 우선적으로 검증
		if (accessTokenHeaderValue != null) {
			val accessTokenDto: AccessTokenDto =
				jwtTokenService.retrieveAccessToken(accessTokenHeaderValue)!!
			setAuthenticationToContext(accessTokenDto.memberId, accessTokenDto.memberRole)
			filterChain.doFilter(request, response)
			return
		}

		// 쿠키에서 가져올 때 AT와 RT 중 하나라도 없으면 실패
		if (accessTokenValue == null || refreshTokenValue == null) {
			filterChain.run { doFilter(request, response) }
			return
		}

		val accessTokenDto: AccessTokenDto? = jwtTokenService.retrieveAccessToken(accessTokenValue)

		// AT가 유효하면 통과
		if (accessTokenDto != null) {
			setAuthenticationToContext(accessTokenDto.memberId, accessTokenDto.memberRole)
			filterChain.doFilter(request, response)
			return
		}

		// AT가 만료된 경우 AT 재발급, 만료되지 않은 경우 null 반환
		val reissuedAccessToken: Optional<AccessTokenDto> =
			Optional.ofNullable(jwtTokenService.reissueAccessTokenIfExpired(accessTokenValue))

		// RT 유효하면 파싱, 유효하지 않으면 null 반환
		val refreshTokenDto: RefreshTokenDto? = jwtTokenService.retrieveRefreshToken(refreshTokenValue)

		// AT가 만료되었고, RT가 유효하면 AT, RT 재발급
		if (reissuedAccessToken.isPresent && refreshTokenDto != null) {
			val accessToken: AccessTokenDto = reissuedAccessToken.get() // 재발급된 AT
			val refreshToken: RefreshTokenDto =
				jwtTokenService.createRefreshTokenDto(refreshTokenDto.memberId)

			// 쿠키에 재발급된 AT, RT 저장
			val httpHeaders: HttpHeaders =
				cookieUtil.generateTokenCookies(
					accessToken.tokenValue,
					refreshToken.tokenValue,
				)
			response.addHeader(
				HttpHeaders.SET_COOKIE,
				httpHeaders.getFirst(ACCESS_TOKEN_COOKIE_NAME),
			)
			response.addHeader(
				HttpHeaders.SET_COOKIE,
				httpHeaders.getFirst(REFRESH_TOKEN_COOKIE_NAME),
			)

			setAuthenticationToContext(accessToken.memberId, accessToken.memberRole)
		}

		// AT, RT가 모두 만료된 경우 실패
		filterChain.doFilter(request, response)
	}

	private fun setAuthenticationToContext(
		memberId: Long,
		memberRole: MemberRole,
	) {
		val userDetails: UserDetails = PrincipalDetails(memberId, memberRole)

		val authentication: Authentication =
			UsernamePasswordAuthenticationToken(
				userDetails,
				null,
				userDetails.authorities,
			)
		SecurityContextHolder.getContext().setAuthentication(authentication)
	}

	private fun extractAccessTokenFromCookie(request: HttpServletRequest): String? {
		return Optional.ofNullable<Cookie>(WebUtils.getCookie(request, ACCESS_TOKEN_COOKIE_NAME))
			.map { obj: Cookie -> obj.value }
			.orElse(null)
	}

	private fun extractRefreshTokenFromCookie(request: HttpServletRequest): String? {
		return Optional.ofNullable<Cookie>(WebUtils.getCookie(request, REFRESH_TOKEN_COOKIE_NAME))
			.map { obj: Cookie -> obj.value }
			.orElse(null)
	}

	companion object {
		private fun extractAccessTokenFromHeader(request: HttpServletRequest): String? {
			return Optional.ofNullable<String>(request.getHeader(HttpHeaders.AUTHORIZATION))
				.filter { header: String -> header.startsWith(TOKEN_PREFIX) }
				.map { header: String -> header.replace(TOKEN_PREFIX, "") }
				.orElse(null)
		}
	}
}
