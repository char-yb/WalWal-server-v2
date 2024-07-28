package com.depromeet.walwal.domain.common

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
	@Column(updatable = false)
	@CreatedDate
	val createdAt: LocalDateTime? = null,
	@Column
	@LastModifiedDate
	val updatedAt: LocalDateTime? = null,
)
