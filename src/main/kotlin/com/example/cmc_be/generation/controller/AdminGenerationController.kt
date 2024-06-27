package com.example.cmc_be.generation.controller

import com.example.cmc_be.common.dto.response.CommonResponse
import com.example.cmc_be.common.dto.response.PageResponse
import com.example.cmc_be.domain.generation.entity.GenerationWeeksInfo
import com.example.cmc_be.generation.dto.PostGenerationInfoReq
import com.example.cmc_be.generation.service.GenerationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
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
        @RequestBody postGenerationInfoReq: PostGenerationInfoReq
    ): CommonResponse<Unit> {
        return CommonResponse.onSuccess(generationService.postGenerationWeeksInfo(postGenerationInfoReq))
    }

    @Operation(summary = "04-02 주차별 모든 날짜 GET API")
    @GetMapping("/date")
    fun getGenerationWeeksInfoDate(
        @RequestParam generation: Int,
    ): CommonResponse<List<GenerationWeeksInfo>> {
        return CommonResponse.onSuccess(generationService.getGenerationWeeksInfoDate(generation))
    }

    @Operation(summary = "04-02 주차별 모든 날짜 PAGE GET API")
    @GetMapping("/date/page")
    fun getGenerationWeeksInfoDatePage(
        @RequestParam generation: Int,
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int,
    ): CommonResponse<PageResponse<GenerationWeeksInfo>> {
        return CommonResponse.onSuccess(generationService.getGenerationWeeksInfoDate(generation, page, size))
    }
}