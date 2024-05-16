package com.example.cmc_be.attendance.dto.req

import java.time.LocalTime

data class HourAndMinute(
    val hour: Int,
    val minute: Int
) {
    fun toLocalTime(): LocalTime = LocalTime.of(hour, minute)
}
