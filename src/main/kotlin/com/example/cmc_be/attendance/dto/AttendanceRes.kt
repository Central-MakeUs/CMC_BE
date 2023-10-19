package com.example.cmc_be.attendance.dto

import com.example.cmc_be.domain.user.enums.UserRole

class AttendanceRes {
    data class AttendanceInfoDto(
        val week: Int,
        val firstHour: Boolean,
        val secondHour: Boolean
    )

    data class AllAttendanceInfoDto(
        val name: String,
        val role: UserRole,
        val nickname: String,
        val attandances: List<AttendanceInfoDto>
    )
}