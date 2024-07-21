package com.depromeet.walwal.domain.auth.domain

import java.security.InvalidParameterException
import java.util.*

enum class OAuthProvider(val value: String?) {
	KAKAO("KAKAO"),
	APPLE("APPLE"),
	;

	companion object {
		fun from(provider: String): OAuthProvider {
			return when (provider.uppercase(Locale.getDefault())) {
				"APPLE" -> APPLE
				"KAKAO" -> KAKAO
				else -> throw InvalidParameterException()
			}
		}
	}
}
