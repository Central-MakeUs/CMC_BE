package com.example.cmc_be.attendance.dto

import com.example.cmc_be.domain.attendance.enums.AttendanceCategory
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.time.LocalTime

class AttendanceRes {
    data class AttendanceInfoDto(
        val week: Int,
        val firstHour: AttendanceCategory,
        val secondHour: AttendanceCategory,
        val isOffline: Boolean,
        val enable: Boolean,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        val date: LocalDate
    )

    data class AttendanceCodeDto(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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