package com.example.cmc_be.attendance.dto.res

import com.example.cmc_be.domain.attendance.enums.AttendanceCategory
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class AttendanceInfo(
    val week: Int,
    val firstHour: AttendanceCategory,
    val secondHour: AttendanceCategory,
    val isOffline: Boolean,
    val enable: Boolean,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate
)