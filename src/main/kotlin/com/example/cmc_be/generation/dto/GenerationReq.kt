package com.example.cmc_be.generation.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

class GenerationReq {
    data class GenerationInfo(
        val generation: Int,
        val week: Int,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        val date: LocalDate,
        val isOffline: Boolean
    )
}