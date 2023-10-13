package com.example.cmc_be.common.dto

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime


@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @Column(name = "createdAt", updatable = false)
    @CreatedDate
    private val createdAt: LocalDateTime? = null

    @Column(name = "updatedAt")
    @LastModifiedDate
    private val updatedAt: LocalDateTime? = null
}
