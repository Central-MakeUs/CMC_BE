package com.example.cmc_be.generation.controller

import com.example.cmc_be.common.response.CommonResponse
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.generation.dto.GenerationReq
import com.example.cmc_be.generation.dto.GenerationRes
import com.example.cmc_be.generation.service.GenerationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "ADMIN-04 Generation 관리자 기수 주차 정보 API")
@RequestMapping("/admin/generations")
class AdminGenerationController(
    private val generationService: GenerationService
) {
    @Operation(summary = "04-01 주차별 정보 POST API")
    @PostMapping
    fun postGenerationWeeksInfo(
        @AuthenticationPrincipal user: User,
        @RequestBody generationInfo: GenerationReq.GenerationInfo
    ): CommonResponse<String> {
        return CommonResponse.onSuccess(generationService.postGenerationWeeksInfo(generationInfo))
    }

    @Operation(summary = "04-02 주차별 모든 날짜 GET API")
    @GetMapping("/date")
    fun getGenerationWeeksInfoDate(
        @AuthenticationPrincipal user: User,
    ): CommonResponse<List<GenerationRes.GenerationWeeksInfoDto>> {
        return CommonResponse.onSuccess(generationService.getGenerationWeeksInfoDate(user))
    }
}