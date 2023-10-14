package com.example.cmc_be.generation.controller

import com.example.cmc_be.common.response.CommonResponse
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.generation.dto.GenerationReq
import com.example.cmc_be.generation.dto.GenerationRes
import com.example.cmc_be.generation.service.GenerationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name ="ADMIN-04 Generation 관리자 기수 주차 정보 API")
@RequestMapping("/admin/generations")
class AdminGenerationController(
    private val generationService: GenerationService
){
    @Operation(summary = "04-01 주차별 정보 POST API")
    @GetMapping("")
    fun postGenerationWeeksInfo(@AuthenticationPrincipal user: User,
                                @RequestBody generationInfo : GenerationReq.GenerationInfo) : CommonResponse<GenerationRes.PostGenerationResponseDto> {
        return CommonResponse.onSuccess(generationService.postGenerationWeeksInfo(user, generationInfo))
    }
}