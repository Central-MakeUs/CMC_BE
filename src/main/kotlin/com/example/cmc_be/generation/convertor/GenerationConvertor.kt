package com.example.cmc_be.generation.convertor

import com.example.cmc_be.common.annotation.Convertor
import com.example.cmc_be.domain.generation.entity.GenerationWeeksInfo
import com.example.cmc_be.generation.dto.GenerationReq

@Convertor
class GenerationConvertor {

    fun postGenerationWeeksInfo(generationInfo: GenerationReq.GenerationInfo): GenerationWeeksInfo {
        return GenerationWeeksInfo(
            generation = generationInfo.generation,
            week = generationInfo.week,
            weekStart = generationInfo.weekStart,
            weekEnd = generationInfo.weekEnd,
            attendanceDate = generationInfo.attendanceDate
        )
    }
}