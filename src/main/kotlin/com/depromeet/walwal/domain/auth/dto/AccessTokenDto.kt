package com.depromeet.walwal.domain.auth.dto

import com.depromeet.walwal.domain.member.domain.MemberRole

data class AccessTokenDto(val memberId: Long, val memberRole: MemberRole, val tokenValue: String)
