package com.depromeet.walwal.domain.member.api

import com.depromeet.walwal.domain.member.application.MemberService
import com.depromeet.walwal.domain.member.domain.Member
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "1-2. [회원]", description = "회원 관련 API입니다.")
@RestController
@RequestMapping("/members")
class MemberController(
	private val memberService: MemberService,
) {
	@Operation(summary = "내 정보 조회", description = "내 정보를 조회하는 API입니다.")
	@GetMapping("/me")
	fun memberInfo(): Member {
		return memberService.findMemberInfo()
	}
}
