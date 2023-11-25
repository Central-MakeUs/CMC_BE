package com.example.cmc_be.generation.dto

import java.time.LocalDate

class GenerationReq {
    data class GenerationInfo(
        val generation: Int,
        val week: Int,
        val date: LocalDate,
        val isOffline: Boolean
    )
}