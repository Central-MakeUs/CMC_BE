package com.example.cmc_be.user.controller

import ApiErrorCodeExample
import com.example.cmc_be.common.response.CommonResponse
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.domain.user.exeption.UserAuthErrorCode
import com.example.cmc_be.user.dto.UserRes
import com.example.cmc_be.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
@Tag(name ="01 User 유저 관련 API")
class UserController(
    private val userService: UserService
) {
    @GetMapping("")
    @ApiErrorCodeExample(UserAuthErrorCode::class)
    @Operation(summary = "01-01 내 정보 조회")
    fun getUserInfo(@AuthenticationPrincipal user: User) : CommonResponse<UserRes.UserInfoDto>{
        return CommonResponse.onSuccess(userService.getUserInfo(user))
    }

    @DeleteMapping("")
    @ApiErrorCodeExample(UserAuthErrorCode::class)
    @Operation(summary = "01-02 유저 탈퇴")
    fun deleteUser(@AuthenticationPrincipal user: User) : CommonResponse<String>{
        userService.deleteUser(user)
        return CommonResponse.onSuccess("탈퇴 성공")
    }

    @GetMapping("/my-page")
    @Operation(summary = "01-03 마이페이지 조회")
    @ApiErrorCodeExample(UserAuthErrorCode::class)
    fun getMyPage(@AuthenticationPrincipal user: User) : CommonResponse<UserRes.MyPageUserInfoDto>{
        return CommonResponse.onSuccess(userService.getMyPage(user))
    }
}