package com.depromeet.walwal.domain.mission.domain

import com.depromeet.walwal.domain.common.BaseTimeEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
class Mission(
	@field:Column(
		name = "title",
		nullable = false,
		length = 100,
	) private var title:
		@Size(max = 100)
		@NotBlank
		String?,
) : BaseTimeEntity() {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mission_id")
	private var id: Long? = null

	fun updateTitle(title: String?) {
		this.title = title
	}
}
