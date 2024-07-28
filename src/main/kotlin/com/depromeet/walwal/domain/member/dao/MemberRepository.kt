package com.depromeet.walwal.domain.member.dao

import com.depromeet.walwal.domain.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, Long> {
	fun findByOauthInfoOauthProviderAndOauthInfoOauthId(
		oauthProvider: String?,
		oauthId: String?,
	): Optional<Member>
}
