package com.example.cmc_be.user.controller

import com.example.cmc_be.common.dto.response.CommonResponse
import com.example.cmc_be.common.dto.response.PageResponse
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.user.dto.user.AdminUserInfoDto
import com.example.cmc_be.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/users")
@Tag(name = "ADMIN-05 유저 정보 API")
class UserAdminController(
    private val userService: UserService
) {
    @GetMapping("/all")
    @Operation(summary = "05-01 특정 기수 유저 정보 전체조회")
    fun getAllUsers(
        @AuthenticationPrincipal user: User,
        @RequestParam generation: Int,
        @RequestParam page: Int,
        @RequestParam size: Int,
    ): CommonResponse<PageResponse<AdminUserInfoDto>> {
        return CommonResponse.onSuccess(
            userService.getAllUserInfoByGeneration(generation, page, size)
        )
    }

    @GetMapping("/management/user")
    @Operation(summary = "05-01 특정 기수 유저 회원가입 수락")
    fun handleSignUpApprove(
        @RequestParam userId: Long,
        @RequestParam approve: Boolean
    ): CommonResponse<User?> {
        return CommonResponse.onSuccess(userService.handleSignUpAprrove(userId, approve))
    }
}