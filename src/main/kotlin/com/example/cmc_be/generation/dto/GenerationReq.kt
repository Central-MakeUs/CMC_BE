package com.example.cmc_be.generation.dto

import com.example.cmc_be.domain.generation.entity.GenerationWeeksInfo
import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class PostGenerationInfoReq(
    @Schema(example = "15")
    val generation: Int,
    @Schema(example = "1")
    val week: Int,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate,
    val isOffline: Boolean
) {

    fun toEntity(): GenerationWeeksInfo {
        return GenerationWeeksInfo(
            generation = this.generation,
            week = this.week,
            date = this.date,
            isOffline = this.isOffline
        )
    }
}
