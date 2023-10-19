package com.example.cmc_be.domain.user.entity

import com.example.cmc_be.common.dto.BaseEntity
import com.example.cmc_be.domain.user.enums.Part
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "UserPart")
@DynamicUpdate
@BatchSize(size = 100)
@DynamicInsert
data class UserPart(
    @Id
    @Column(name = "id") @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = 0L,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false, updatable = false)
    private val user: User,
    @Enumerated(EnumType.STRING) val part: Part,
    val generation: Int,
) : BaseEntity() {
}