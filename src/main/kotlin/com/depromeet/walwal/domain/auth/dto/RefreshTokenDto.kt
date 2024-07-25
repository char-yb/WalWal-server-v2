package com.depromeet.walwal.domain.auth.dto

data class RefreshTokenDto(val memberId: Long, val tokenValue: String, val ttl: Long)
