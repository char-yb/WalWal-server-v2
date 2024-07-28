package com.depromeet.walwal.domain.common

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import kotlin.reflect.full.isSubclassOf

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	@Column(updatable = false)
	@CreatedDate
	val createdAt: LocalDateTime? = null,
	@Column
	@LastModifiedDate
	val updatedAt: LocalDateTime? = null,
) {
	// Proxy 객체 고려하여 equals Override, https://zins.tistory.com/19
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is BaseEntity) return false
		if (!compareClassesIncludeProxy(other)) return false
		if (id == null || other.id == null) return false

		return id == other.id
	}

	private fun compareClassesIncludeProxy(other: Any) =
		this::class.isSubclassOf(other::class) ||
			other::class.isSubclassOf(this::class)

	override fun hashCode(): Int = id?.hashCode() ?: 0
}
