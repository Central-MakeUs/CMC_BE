package com.example.cmc_be.attendance.dto.res

import com.example.cmc_be.domain.attendance.entity.AttendanceCode

data class QrSchemata(
    val androidSchema: String,
    val iosSchema: String,
    val attendanceCode: AttendanceCode,
)