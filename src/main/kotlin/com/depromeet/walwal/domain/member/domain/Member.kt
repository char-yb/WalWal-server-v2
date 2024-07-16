package com.depromeet.walwal.domain.member.domain

import com.depromeet.walwal.domain.common.BaseTimeEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "member")
@Entity
class Member : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private var id: Long? = null

    @Embedded
    private val profile = Profile.createProfile("", "")

    @Embedded
    private val oauthInfo: OauthInfo? = null

    @Enumerated(EnumType.STRING)
    private val status: MemberStatus? = null

    @Enumerated(EnumType.STRING)
    private val role: MemberRole? = null

    private val lastLoginAt: LocalDateTime? = null
}
