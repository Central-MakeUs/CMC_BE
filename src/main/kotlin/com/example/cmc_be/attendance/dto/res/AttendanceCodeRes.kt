package com.example.cmc_be.attendance.dto.res

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.time.LocalTime

data class AttendanceCodeRes(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val availableDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val lateMinute: Long
)