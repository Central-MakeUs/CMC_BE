package com.example.cmc_be.generation.controller

import com.example.cmc_be.generation.service.GenerationService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/generations")
@Tag(name = "04 Generation 기수 주차 정보 API")
class GenerationController(
    private val generationService: GenerationService
) {
    
}