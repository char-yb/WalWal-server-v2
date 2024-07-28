package com.depromeet.walwal.global.security

import com.depromeet.walwal.domain.member.domain.MemberRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class PrincipalDetails(
	private val memberId: Long,
	private val role: MemberRole,
) : UserDetails {
	override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
		return mutableListOf(SimpleGrantedAuthority(role.value))
	}

	override fun getPassword(): String? {
		return null
	}

	override fun getUsername(): String {
		return memberId.toString()
	}

	override fun isAccountNonExpired(): Boolean {
		return true
	}

	override fun isAccountNonLocked(): Boolean {
		return true
	}

	override fun isCredentialsNonExpired(): Boolean {
		return true
	}

	override fun isEnabled(): Boolean {
		return true
	}
}
