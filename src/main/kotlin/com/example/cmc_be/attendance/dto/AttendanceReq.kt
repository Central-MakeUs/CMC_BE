package com.example.cmc_be.attendance.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalTime

class AttendanceReq {

    data class AttendanceCode(
        val code: String
    )

    data class GenerateCode(
        @Schema(example = "14")
        val generation: Int,
        @Schema(example = "1")
        val week: Int,
        @Schema(example = "1")
        val hour: Int,
        val availableDate: LocalDate,
        val startTime: HourAndMinute,
        val endTime: HourAndMinute,
    )

    data class HourAndMinute(
        val hour: Int,
        val minute: Int
    ) {
        fun toLocalTime(): LocalTime = LocalTime.of(hour, minute)
    }
}