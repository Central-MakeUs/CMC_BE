package com.example.cmc_be.notice.controller

import com.example.cmc_be.common.response.CommonResponse
import com.example.cmc_be.common.response.PageResponse
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.notice.dto.NotificationRes
import com.example.cmc_be.notice.service.NotificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notifications")
@Tag(name = "02 Notification 공지 관련 API")
class NotificationController(
    private val notificationService: NotificationService
) {
    @GetMapping("/latest")
    @Operation(summary = "02-01 본인 기수 최신 공지 조회")
    fun getThisWeekNotification(@AuthenticationPrincipal user: User): CommonResponse<NotificationRes.NotificationDto> {
        return CommonResponse.onSuccess(notificationService.getThisWeekNotification(user))
    }

    @GetMapping("/all")
    @Operation(summary = "02-02 본인 기수 전체 공지 조회")
    fun getAllNotification(@AuthenticationPrincipal user: User): CommonResponse<List<NotificationRes.NotificationDto>> {
        return CommonResponse.onSuccess(notificationService.getAllNotification(user))
    }

    @GetMapping()
    @Operation(summary = "02-03 본인 기수 공지 페이징 조회")
    fun getAllNotificationPaging(
        @AuthenticationPrincipal user: User,
        @RequestParam("page") page: Int,
        @RequestParam("size") size: Int,
    ): CommonResponse<PageResponse<NotificationRes.NotificationDto>> {
        return CommonResponse.onSuccess(notificationService.getNotificationPaging(user, page, size))
    }
}