package com.depromeet.walwal.domain.member.dao

import com.depromeet.walwal.domain.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>
