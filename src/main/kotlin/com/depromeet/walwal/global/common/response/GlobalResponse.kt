package com.depromeet.walwal.global.common.response

import com.depromeet.walwal.global.error.ErrorResponse
import java.time.LocalDateTime

data class GlobalResponse(val success: Boolean, val status: Int, val data: Any, val timestamp: LocalDateTime) {
	companion object {
		@JvmStatic
		fun success(
			status: Int,
			data: Any,
		): GlobalResponse {
			return GlobalResponse(true, status, data, LocalDateTime.now())
		}

		fun fail(
			status: Int,
			errorResponse: ErrorResponse,
		): GlobalResponse {
			return GlobalResponse(false, status, errorResponse, LocalDateTime.now())
		}
	}
}
