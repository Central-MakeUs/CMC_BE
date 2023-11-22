package com.example.cmc_be.user.controller

import ApiErrorCodeExample
import com.example.cmc_be.common.response.CommonResponse
import com.example.cmc_be.domain.user.exeption.LoginUserErrorCode
import com.example.cmc_be.domain.user.exeption.SendEmailErrorCode
import com.example.cmc_be.user.dto.AuthReq
import com.example.cmc_be.user.dto.AuthRes
import com.example.cmc_be.user.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name ="00 Auth 유저 인증 관련 API")
@RequestMapping("/auth")
class AuthController(
    val authService: AuthService
) {
    @PostMapping("/sign-up")
    @Operation(summary = "00-01 회원가입")
    @ApiErrorCodeExample(SendEmailErrorCode::class)
    fun signUpUser(@Valid @RequestBody signUpUserDto: AuthReq.SignUpUserDto) : CommonResponse<AuthRes.UserTokenDto> {
        return CommonResponse.onSuccess(authService.signUpUser(signUpUserDto))
    }

    @PostMapping("/log-in")
    @Operation(summary = "00-01 로그인")
    @ApiErrorCodeExample(LoginUserErrorCode::class)
    fun logInUser(@Valid @RequestBody loginUserDto : AuthReq.LoginUserDto) : CommonResponse<AuthRes.UserTokenDto>{
        return CommonResponse.onSuccess(authService.logInUser(loginUserDto))
    }

    @GetMapping("/email")
    @Operation(summary = "00-03 이메일 중복체크")
    @ApiErrorCodeExample(SendEmailErrorCode::class)
    fun checkEmail(@Parameter(description = "이메일") @RequestParam email : String) : CommonResponse<String>{
        authService.checkEmail(email);
        return CommonResponse.onSuccess("사용 가능");
    }

    /*
        이메일 인증 번호 보내기 for 비밀번호 찾기
     */

    @GetMapping("/password/email")
    @Operation(summary = "00-04 비밀번호 찾기용 이메일 인증번호 전송")
    @ApiErrorCodeExample(SendEmailErrorCode::class)
    fun sendEmail(@RequestParam email : String) : CommonResponse<String>{
        authService.sendEmail(email)
        return CommonResponse.onSuccess("이메일 전송 성공")
    }


    /*
        이메일 인증번호 확인 용
     */

    /*
        비밀번호 찾기
     */

}