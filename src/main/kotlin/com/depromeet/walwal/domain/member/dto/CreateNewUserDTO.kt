package com.depromeet.walwal.domain.member.dto

import com.depromeet.walwal.domain.auth.domain.OAuthProvider
import com.depromeet.walwal.domain.member.domain.MarketingAgreement
import com.depromeet.walwal.domain.member.domain.RaisePet

data class CreateNewUserDTO(
	val provider: OAuthProvider,
	val oauthId: String,
	val nickname: String,
	val raisePet: RaisePet,
	val profileImageUrl: String,
	val email: String,
	val marketingAgreement: MarketingAgreement,
) {
	companion object {
		fun of(
			provider: OAuthProvider,
			oauthId: String,
			nickname: String,
			raisePet: RaisePet,
			profileImageUrl: String,
			email: String,
			marketingAgreement: MarketingAgreement,
		): CreateNewUserDTO {
			return CreateNewUserDTO(
				provider,
				oauthId,
				nickname,
				raisePet,
				profileImageUrl,
				email,
				marketingAgreement,
			)
		}
	}
}
