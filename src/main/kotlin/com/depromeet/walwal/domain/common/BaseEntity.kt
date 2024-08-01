package com.depromeet.walwal.domain.common

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity {
	@Column(updatable = false)
	@CreationTimestamp
	val createdAt: LocalDateTime? = LocalDateTime.now()

	@UpdateTimestamp
	@Column
	var updatedAt: LocalDateTime? = null
		protected set
}
