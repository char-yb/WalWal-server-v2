package com.depromeet.walwal.domain.auth.api

import com.depromeet.walwal.domain.auth.application.AuthService
import com.depromeet.walwal.domain.auth.domain.OAuthProvider
import com.depromeet.walwal.domain.auth.dto.request.RefreshTokenRequest
import com.depromeet.walwal.domain.auth.dto.request.SocialLoginRequest
import com.depromeet.walwal.domain.auth.dto.response.AuthTokenResponse
import com.depromeet.walwal.domain.auth.dto.response.SocialClientResponse
import com.depromeet.walwal.domain.member.dto.request.CreateMemberRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@Tag(name = "1-1. [인증]", description = "인증 관련 API입니다.")
@RestController
@RequestMapping("/auth")
class AuthController(
	private val authService: AuthService,
) {
	@Operation(summary = "소셜 로그인", description = "소셜 로그인 후 임시 토큰을 발급합니다.")
	@PostMapping("/social-login/{provider}")
	fun socialLogin(
		@PathVariable(name = "provider") @Parameter(example = "apple", description = "OAuth 제공자") provider: String,
		@RequestBody request: @Valid SocialLoginRequest,
	): AuthTokenResponse {
		val oAuthProvider: OAuthProvider = OAuthProvider.from(provider)

		val socialClientResponse: SocialClientResponse =
			authService.authenticateFromProvider(oAuthProvider, request.token)

		// 위 결과에서 나온 oauthId와 email로 토큰 발급
		return authService.socialLogin(
			oAuthProvider,
			socialClientResponse.oauthId,
			socialClientResponse.email,
		)
	}

	@Operation(summary = "회원가입", description = "회원가입을 진행 후 토큰 발급")
	@PostMapping("/register")
	fun register(
		@RequestBody request: @Valid CreateMemberRequest,
	): AuthTokenResponse {
		return authService.registerMember(request)
	}

	@Operation(summary = "리프레시 토큰 발급", description = "리프레시 토큰을 이용해 새로운 액세스 토큰을 발급합니다.")
	@PostMapping("/reissue")
	fun reissueTokenPair(
		@RequestBody request: @Valid RefreshTokenRequest,
	): AuthTokenResponse {
		return authService.reissueTokenPair(request)
	}
}
