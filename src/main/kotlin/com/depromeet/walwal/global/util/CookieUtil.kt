package com.depromeet.walwal.global.util

import com.depromeet.walwal.global.common.constants.SecurityConstants.ACCESS_TOKEN_COOKIE_NAME
import com.depromeet.walwal.global.common.constants.SecurityConstants.REFRESH_TOKEN_COOKIE_NAME
import org.springframework.boot.web.server.Cookie
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component

@Component
class CookieUtil {
	fun generateTokenCookies(
		accessToken: String,
		refreshToken: String,
	): HttpHeaders {
		val sameSite = determineSameSitePolicy()

		val accessTokenCookie: ResponseCookie =
			ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, accessToken)
				.path("/")
				.secure(true)
				.sameSite(sameSite)
				.httpOnly(true)
				.build()

		val refreshTokenCookie: ResponseCookie =
			ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
				.path("/")
				.secure(true)
				.sameSite(sameSite)
				.httpOnly(true)
				.build()

		val headers = HttpHeaders()
		headers.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
		headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())

		return headers
	}

	private fun determineSameSitePolicy(): String {
		// TODO: prod 환경 구성 시 주석 해제
		// if (springEnvironmentUtil.isProdProfile()) {
		//     return Cookie.SameSite.STRICT.attributeValue();
		// }
		return Cookie.SameSite.NONE.attributeValue()
	}
}
