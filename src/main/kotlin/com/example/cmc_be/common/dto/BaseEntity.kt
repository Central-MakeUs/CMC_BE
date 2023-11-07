package com.example.cmc_be.common.dto

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @Column(name = "createdAt", updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime? = null

    @Column(name = "updatedAt")
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null
}