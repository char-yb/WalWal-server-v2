package com.depromeet.walwal.domain.common

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
	@Column(updatable = false, nullable = false)
	@CreationTimestamp
	var createdAt: LocalDateTime = LocalDateTime.now(),
	@Column
	@UpdateTimestamp
	var updatedAt: LocalDateTime? = null,
)
