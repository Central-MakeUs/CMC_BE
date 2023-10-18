package com.example.cmc_be.attendance.dto

class AttendanceRes {
    data class AttendanceInfoDto(
        val week: Int,
        val firstHour: Boolean,
        val secondHour: Boolean
    )
}