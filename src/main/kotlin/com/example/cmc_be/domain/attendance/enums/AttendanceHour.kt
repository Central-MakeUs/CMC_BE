package com.example.cmc_be.domain.attendance.enums

enum class AttendanceHour(
    val value : String,
    val hour: String
) {
    FIRST_HOUR("FIRST HOUR","1차시"),
    SECOND_HOUR("SECOND HOUR","2차시")
}