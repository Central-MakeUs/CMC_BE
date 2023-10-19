package com.example.cmc_be.domain.notification.entity

import com.example.cmc_be.common.dto.BaseEntity
import com.example.cmc_be.domain.generation.entity.GenerationWeeksInfo
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "Notification")
@DynamicUpdate
@BatchSize(size = 20)
@DynamicInsert
data class Notification(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weekId", nullable = false, updatable = false)
    val generationWeeksInfo: GenerationWeeksInfo,
    val title: String,
    val notionUrl: String
) : BaseEntity()
