package com.depromeet.walwal.domain.mission.domain

import com.depromeet.walwal.domain.common.BaseTimeEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Table(name = "mission")
@Entity
class Mission(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mission_id")
	var id: Long,
	@Column(
		name = "title",
		nullable = false,
		length = 100,
	)
	@Size(max = 100)
	@NotBlank
	var title: String?,
) : BaseTimeEntity() {
	fun updateTitle(title: String?) {
		this.title = title
	}
}
