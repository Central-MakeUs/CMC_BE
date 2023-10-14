package com.example.cmc_be.domain.generation.repository

import com.example.cmc_be.domain.generation.entity.GenerationWeeksInfo
import org.springframework.data.jpa.repository.JpaRepository

interface GenerationWeeksInfoRepository : JpaRepository<GenerationWeeksInfo, Long> {

    fun findAllByGeneration(generation: Int): List<GenerationWeeksInfo>

    fun findByGenerationAndWeek(generation: Int, week: Int): GenerationWeeksInfo?
}