package com.example.cmc_be.generation.service

import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.generation.dto.GenerationReq
import com.example.cmc_be.generation.dto.GenerationRes
import org.springframework.stereotype.Service

@Service
class GenerationService {
    fun getGenerationWeeksInfo(user: User): List<GenerationRes.GenerationWeeksInfoDto> {
        TODO("Not yet implemented")
    }

    fun postGenerationWeeksInfo(
        user: User,
        generationInfo: GenerationReq.GenerationInfo
    ): GenerationRes.PostGenerationResponseDto {
        TODO("Not yet implemented")
    }
}