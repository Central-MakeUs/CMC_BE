package com.example.cmc_be.domain.attendance.enums

enum class AttendanceStatus(
    val value : String,
    val status: String
) {
    ATTENDANCE("ATTENDANCE","출석 완료"),
    ABSENT("ABSENT","결석")
}