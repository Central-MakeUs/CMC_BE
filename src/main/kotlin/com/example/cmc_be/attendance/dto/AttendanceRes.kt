package com.example.cmc_be.attendance.dto

import com.example.cmc_be.domain.attendance.enums.AttendanceCategory
import java.time.LocalDate
import java.time.LocalTime

class AttendanceRes {
    data class AttendanceInfoDto(
        val week: Int,
        val firstHour: AttendanceCategory,
        val secondHour: AttendanceCategory,
        val isOffline: Boolean,
        val enable: Boolean,
    )

    data class AttendanceCodeDto(
        val availableDate: LocalDate,
        val startTime: LocalTime,
        val endTime: LocalTime,
        val lateMinute: Long
    )

    data class AllAttendanceInfoDto(
        val name: String,
        val role: String,
        val nickname: String,
        val attendanceStatus: AttendanceStatus,
        val attandances: List<AttendanceInfoDto>
    )

    data class GetAttendances(
        val attendanceStatus: AttendanceStatus,
        val attandances: List<AttendanceInfoDto>
    )

    data class AttendanceStatus(
        val attendanceCount: Int,
        val lateCount: Int,
        val absentCount: Int
    )
}