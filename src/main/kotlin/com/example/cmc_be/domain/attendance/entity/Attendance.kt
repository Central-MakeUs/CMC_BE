package com.example.cmc_be.domain.attendance.entity

import com.example.cmc_be.common.dto.BaseEntity
import com.example.cmc_be.domain.attendance.enums.AttendanceCategory
import com.example.cmc_be.domain.attendance.enums.AttendanceHour
import com.example.cmc_be.domain.generation.entity.GenerationWeeksInfo
import com.example.cmc_be.domain.user.entity.User
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "Attendance")
@DynamicUpdate
@BatchSize(size = 10)
@DynamicInsert
class Attendance(
    @Id
    @Column(name = "id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false, updatable = false)
    val user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generationWeeksInfoId", nullable = false, updatable = false)
    val generationWeeksInfo: GenerationWeeksInfo,
    @Enumerated(EnumType.STRING)
    val attendanceCategory: AttendanceCategory,
    @Enumerated(EnumType.STRING)
    val attendanceHour: AttendanceHour
) : BaseEntity() {

}