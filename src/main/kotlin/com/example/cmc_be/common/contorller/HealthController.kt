package com.example.cmc_be.common.contorller

import com.backend.cmcapi.common.annotation.ApiErrorCodeExample
import com.example.cmc_be.user.exeption.UserAuthErrorCode
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
@Tag(name = "Health Check 🖐️", description = "Health Check.")
class HealthController {

    @GetMapping("")
    @ApiErrorCodeExample(UserAuthErrorCode::class)
    @Operation(summary = "HealthCheck 용 API 입니다", description = "헬스체크")
    fun healthCheck(): String? {
        return "I'm Healthy Server"
    }
}