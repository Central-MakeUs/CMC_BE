package com.example.cmc_be.attendance.dto.req

import io.swagger.v3.oas.annotations.media.Schema

data class GenerateCode(
    @Schema(example = "14")
    val generation: Int,
    @Schema(example = "1")
    val week: Int,
    @Schema(example = "1")
    val hour: Int,
    val startTime: HourAndMinute,
    val endTime: HourAndMinute,
    @Schema(example = "15")
    val lateMinute: Long,
)