package com.depromeet.walwal.domain.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {

    @Column(updatable = false)
    @CreatedDate
    val createdAt: LocalDateTime? = null

    @Column
    @LastModifiedDate
    val updatedAt: LocalDateTime? = null
}