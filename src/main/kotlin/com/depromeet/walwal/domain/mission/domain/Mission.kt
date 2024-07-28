package com.depromeet.walwal.domain.mission.domain

import com.depromeet.walwal.domain.common.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

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
