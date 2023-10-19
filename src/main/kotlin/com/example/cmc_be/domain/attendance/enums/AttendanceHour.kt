package com.example.cmc_be.domain.attendance.enums

import com.example.cmc_be.common.exeption.NotFoundException
import com.example.cmc_be.domain.attendance.exception.AttendanceErrorCode

enum class AttendanceHour(
    val value: String,
    val hour: String
) {
    FIRST_HOUR("FIRST HOUR", "1차시"),
    SECOND_HOUR("SECOND HOUR", "2차시");

    companion object {
        fun of(intHour: Int): AttendanceHour {
            return if (intHour == 1) FIRST_HOUR
            else if (intHour == 2) SECOND_HOUR
            else throw NotFoundException(AttendanceErrorCode.NOT_EXIST_HOUR)
        }
    }
}