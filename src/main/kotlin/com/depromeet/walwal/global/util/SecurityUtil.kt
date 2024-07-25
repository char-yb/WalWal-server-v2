package com.depromeet.walwal.global.util

import com.depromeet.walwal.global.error.exception.CustomException
import com.depromeet.walwal.global.error.exception.ErrorCode
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityUtil {
	val currentMemberId: Long
		get() {
			val authentication: Authentication = SecurityContextHolder.getContext().authentication
			try {
				return authentication.name.toLong()
			} catch (e: Exception) {
				throw CustomException(ErrorCode.AUTH_NOT_FOUND)
			}
		}

	val currentMemberRole: String
		get() {
			val authentication: Authentication = SecurityContextHolder.getContext().authentication
			try {
				return authentication.authorities.stream().findFirst().get().authority
			} catch (e: Exception) {
				throw CustomException(ErrorCode.AUTH_NOT_FOUND)
			}
		}
}
