package com.depromeet.walwal.domain.mission.domain

import com.depromeet.walwal.domain.common.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import kotlin.reflect.full.isSubclassOf

@Table(name = "mission")
@Entity
class Mission(
	@Column(
		name = "title",
		nullable = false,
		length = 100,
	)
	@Size(max = 100)
	@NotBlank
	var title: String,
) : BaseEntity() {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mission_id")
	var id: Long? = null

	constructor() : this(
		title = "",
	)

	// Proxy 객체 고려하여 equals Override, https://zins.tistory.com/19
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Mission) return false
		if (!compareClassesIncludeProxy(other)) return false
		if (id == null) return false
		if (id != other.id) return false
		return true
	}

	private fun compareClassesIncludeProxy(other: Any) =
		this::class.isSubclassOf(other::class) ||
			other::class.isSubclassOf(this::class)

	override fun hashCode(): Int = id?.hashCode() ?: 0

	companion object {
		fun createMission(title: String): Mission {
			with(Mission) {
				return Mission(
					title = title,
				)
			}
		}
	}

	fun updateTitle(title: String) {
		this.title = title
	}
}
