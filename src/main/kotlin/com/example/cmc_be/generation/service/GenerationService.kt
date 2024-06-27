package com.example.cmc_be.generation.service

import com.example.cmc_be.common.dto.response.PageResponse
import com.example.cmc_be.domain.generation.entity.GenerationWeeksInfo
import com.example.cmc_be.domain.generation.repository.GenerationWeeksInfoRepository
import com.example.cmc_be.generation.dto.PostGenerationInfoReq
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class GenerationService(
    private val generationWeeksInfoRepository: GenerationWeeksInfoRepository,
) {
    fun getGenerationWeeksInfoDate(generation: Int): List<GenerationWeeksInfo> {
        return generationWeeksInfoRepository.findAllByGeneration(generation)
    }

    fun postGenerationWeeksInfo(
        postGenerationInfoReq: PostGenerationInfoReq
    ) {
        generationWeeksInfoRepository.save(postGenerationInfoReq.toEntity())
    }

    fun getGenerationWeeksInfoDate(generation: Int, page: Int, size: Int): PageResponse<GenerationWeeksInfo> {
        val pageable = PageRequest.of(page, size, Sort.by("id").descending())
        return PageResponse.from(generationWeeksInfoRepository.findAll(pageable))
    }
}