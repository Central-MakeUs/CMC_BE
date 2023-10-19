package com.example.cmc_be.generation.dto

import java.time.LocalDate

class GenerationReq {
    data class GenerationInfo(
        val generation: Int,
        val week: Int,
        val weekStart: LocalDate,
        val weekEnd: LocalDate,
        val attendanceDate: LocalDate
    )
}