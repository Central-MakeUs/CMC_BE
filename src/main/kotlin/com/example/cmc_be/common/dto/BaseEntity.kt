package com.example.cmc_be.common.dto

import lombok.Getter
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @Column(name = "createdAt", updatable = false)
    @CreatedDate
    private val createdAt: LocalDateTime? = null

    @Column(name = "updatedAt")
    @LastModifiedDate
    private val updatedAt: LocalDateTime? = null
}
