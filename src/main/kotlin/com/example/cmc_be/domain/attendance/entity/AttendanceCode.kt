package com.example.cmc_be.domain.attendance.entity

import com.example.cmc_be.common.exeption.BadRequestException
import com.example.cmc_be.domain.attendance.enums.AttendanceHour
import com.example.cmc_be.domain.attendance.exception.AttendanceErrorCode
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

@Entity
@Table(name = "AttendanceCode")
@DynamicUpdate
@BatchSize(size = 10)
@DynamicInsert
data class AttendanceCode(
    @Id
    @Column(name = "id")
    val id: String,
    val generation: Int,
    val week: Int,
    @Enumerated(EnumType.STRING) val hour: AttendanceHour,
    val availableDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val lateMinute: Long,
) {
    fun validate(): BadRequestException? {
        val currentDateTime = ZonedDateTime.now(ZoneId.systemDefault())
        val currentDate = currentDateTime.toLocalDate()
        val currentTime = currentDateTime.toLocalTime()

        if (!currentDate.isEqual(availableDate)) {
            return BadRequestException(AttendanceErrorCode.INVALID_CODE)
        }
        if (currentTime.isBefore(startTime) || currentTime.isAfter(endTime.plusMinutes(lateMinute))) {
            return BadRequestException(AttendanceErrorCode.OVERDUE_DATE)
        }
        return null
    }

    fun isLate(): Boolean {
        val currentDateTime = ZonedDateTime.now(ZoneId.systemDefault())
        val currentTime = currentDateTime.toLocalTime()
        return (currentTime.isBefore(endTime.plusMinutes(lateMinute + 1)) && currentTime.isAfter(endTime))
    }

}