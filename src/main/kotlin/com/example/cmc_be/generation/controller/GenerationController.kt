package com.example.cmc_be.generation.controller

import com.example.cmc_be.common.response.CommonResponse
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.generation.dto.GenerationRes
import com.example.cmc_be.generation.service.GenerationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/generations")
@Tag(name ="04 Generation 기수 주차 정보 API")
class GenerationController(
    private val generationService: GenerationService
) {
    @Operation(summary = "04-01 주차별 날짜 GET API")
    @GetMapping("")
    fun getGenerationWeeksInfo(@AuthenticationPrincipal user: User) : CommonResponse<List<GenerationRes.GenerationWeeksInfoDto>>{
        return CommonResponse.onSuccess(generationService.getGenerationWeeksInfo(user))
    }
}