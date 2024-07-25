package com.depromeet.walwal.domain.auth.dto.response

import java.util.*

data class AppleKeyListResponse(val keys: Array<AppleKeyResponse>) {
	override fun equals(obj: Any?): Boolean {
		return (
			obj is AppleKeyListResponse &&
				obj.keys.contentEquals(keys)
		)
	}

	override fun hashCode(): Int {
		return keys.contentHashCode()
	}

	override fun toString(): String {
		return "AppleKeyListResponse{" + "keys=" + keys.contentToString() + '}'
	}

	fun getMatchedKeyBy(
		kid: String,
		alg: String,
	): Optional<AppleKeyResponse> {
		return Arrays.stream(keys)
			.filter { key: AppleKeyResponse -> key.kid == kid && key.alg == alg }
			.findFirst()
	}
}
