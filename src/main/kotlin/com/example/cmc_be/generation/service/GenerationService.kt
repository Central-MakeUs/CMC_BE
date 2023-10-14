package com.example.cmc_be.generation.service

import com.example.cmc_be.domain.generation.repository.GenerationWeeksInfoRepository
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.generation.convertor.GenerationConvertor
import com.example.cmc_be.generation.dto.GenerationReq
import org.springframework.stereotype.Service

@Service
class GenerationService(
    private val generationWeeksInfoRepository: GenerationWeeksInfoRepository,
    private val generationConvertor: GenerationConvertor
) {
    fun getGenerationWeeksInfoDate(user: User): List<String> {
        return generationWeeksInfoRepository.findAllByGeneration(user.nowGeneration)
            .map { "${it.generation}th ${it.week}week start at : ${it.weekStart} and end at : ${it.weekEnd}, attandance : ${it.attendanceDate}" }
    }

    fun postGenerationWeeksInfo(
        generationInfo: GenerationReq.GenerationInfo
    ): String {
        return generationConvertor.postGenerationWeeksInfo(generationInfo).let {
            generationWeeksInfoRepository.save(it)
            "${it.generation}기수 ${it.week}주차 정보 저장 완료"
        }
    }
}