package com.depromeet.walwal.domain.auth.application.kakao

import com.depromeet.walwal.domain.auth.dto.response.KakaoAuthResponse
import com.depromeet.walwal.domain.auth.dto.response.SocialClientResponse
import com.depromeet.walwal.global.common.constants.SecurityConstants.KAKAO_USER_ME_URL
import com.depromeet.walwal.global.common.constants.SecurityConstants.TOKEN_PREFIX
import com.depromeet.walwal.global.error.exception.CustomException
import com.depromeet.walwal.global.error.exception.ErrorCode
import org.springframework.http.HttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse
import java.util.*

@Component
class KakaoClient(
	private val restClient: RestClient,
) {
	fun authenticateFromKakao(token: String): SocialClientResponse {
		val kakaoAuthResponse =
			restClient
				.get()
				.uri(KAKAO_USER_ME_URL)
				.header("Authorization", TOKEN_PREFIX + token)
				.exchange<KakaoAuthResponse>
				{ _: HttpRequest?, response: ConvertibleClientHttpResponse ->
					if (!response.statusCode.is2xxSuccessful) {
						throw CustomException(
							ErrorCode.APPLE_TOKEN_CLIENT_FAILED,
						)
					}
					Objects.requireNonNull(
						response.bodyTo(KakaoAuthResponse::class.java),
					)!!
				}

		return SocialClientResponse(
			kakaoAuthResponse.kakaoAccount.email,
			kakaoAuthResponse.id.toString(),
		)
	}
}
