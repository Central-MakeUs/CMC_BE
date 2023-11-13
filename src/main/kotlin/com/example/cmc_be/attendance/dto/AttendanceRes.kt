package com.example.cmc_be.attendance.dto

import com.example.cmc_be.domain.attendance.enums.AttendanceStatus
import com.example.cmc_be.domain.user.enums.UserRole
import java.time.LocalDate
import java.time.LocalTime

class AttendanceRes {
    data class AttendanceInfoDto(
        val week: Int,
        val firstHour: AttendanceStatus,
        val secondHour: AttendanceStatus
    )

    data class AttendanceCodeDto(
        val availableDate: LocalDate,
        val startTime: LocalTime,
        val endTime: LocalTime,
        val lateMinute: Long
    )

    data class AllAttendanceInfoDto(
        val name: String,
        val role: UserRole,
        val nickname: String,
        val attandances: List<AttendanceInfoDto>
    )
}