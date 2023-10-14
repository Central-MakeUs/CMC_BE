package com.example.cmc_be.notice.controller

import com.example.cmc_be.common.response.CommonResponse
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.notice.dto.NotificationRes
import com.example.cmc_be.notice.service.NotificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/Notifications")
@Tag(name ="02 Notification 공지 관련 API")
class NotificationController(
    private val notificationService: NotificationService
) {
    @GetMapping("")
    @Operation(summary = "02-01 이번주 차 공지 조회")
    fun getThisWeekNotification(@AuthenticationPrincipal user: User) : CommonResponse<List<NotificationRes.NotificationDto>>{
        return CommonResponse.onSuccess(notificationService.getThisWeekNotification(user))
    }
    @GetMapping("/all")
    @Operation(summary = "02-02 전체 공지 조회")
    fun getAllNotification(@AuthenticationPrincipal user: User) : CommonResponse<List<NotificationRes.NotificationDto>>{
        return CommonResponse.onSuccess(notificationService.getAllNotification(user))
    }
}