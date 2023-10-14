package com.example.cmc_be.domain.generation.entity

import com.example.cmc_be.common.dto.BaseEntity
import com.example.cmc_be.domain.generation.enums.Weeks
import com.example.cmc_be.domain.user.enums.Generation
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDate

@Entity
@Table(name = "GenerationWeeksInfo")
@DynamicUpdate
@BatchSize(size = 20)
@DynamicInsert
class GenerationWeeksInfo(
    @Id
    @Column(name = "id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0L,
    @Enumerated(EnumType.STRING) val generation : Generation,
    @Enumerated(EnumType.STRING) val week: Weeks,
    val weekStart : LocalDate,
    val weekEnd : LocalDate,
    val attendanceDate : LocalDate
) : BaseEntity() {
}