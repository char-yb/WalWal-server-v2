package com.depromeet.walwal.domain.member.dto.request

import com.depromeet.walwal.domain.member.domain.MarketingAgreement
import com.depromeet.walwal.domain.member.domain.RaisePet

data class CreateMemberRequest(
	val nickname: String,
	val profileImageUrl: String,
	val raisePet: RaisePet,
	val email: String,
	val marketingAgreement: MarketingAgreement,
)
