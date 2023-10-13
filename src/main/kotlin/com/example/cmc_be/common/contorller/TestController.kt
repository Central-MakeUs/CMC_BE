package com.example.cmc_be.common.contorller

import com.example.cmc_be.common.exeption.BadRequestException
import com.example.cmc_be.common.exeption.errorcode.TestErrorCode
import com.example.cmc_be.common.response.CommonResponse
import com.example.cmc_be.common.security.JwtService
import com.example.cmc_be.domain.user.entity.User
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Test 도메인")
@RequestMapping("/tests")
class TestController(
    private val jwtService: JwtService
) {
    val log = KotlinLogging.logger {}

    @GetMapping("/{userId}")
    fun createJwt(@PathVariable("userId") userId: Long): CommonResponse<String> {
        val token: String = jwtService.createToken(userId)
        val response = CommonResponse.onSuccess(token)
        if (userId != 1L) throw BadRequestException(TestErrorCode.TEST_ERROR_CODE)
        log.info { response }
        return CommonResponse.onSuccess(token)
    }

    @GetMapping("/check-token")
    fun checkToken(@AuthenticationPrincipal user: User): CommonResponse<String> {
        return CommonResponse.onSuccess("성공")
    }

}