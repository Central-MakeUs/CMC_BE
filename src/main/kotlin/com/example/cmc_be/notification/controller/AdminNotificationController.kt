package com.example.cmc_be.notification.controller

import com.example.cmc_be.common.response.CommonResponse
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.notification.dto.NotificationReq
import com.example.cmc_be.notification.dto.NotificationRes
import com.example.cmc_be.notification.service.NotificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/notifications")
@Tag(name = "ADMIN-02 Notification 공지 관련 API")
class AdminNotificationController(
    private val notificationService: NotificationService
) {

    @PostMapping("")
    @Operation(summary = "02-01 공지 업로드")
    fun postNotification(@RequestBody notificationInfo: NotificationReq.NotificationInfo): CommonResponse<String> {
        return CommonResponse.onSuccess(notificationService.upsertNotification(notificationInfo))
    }

    @GetMapping("/all/{generation}")
    @Operation(summary = "02-02 기수 전체 공지 조회")
    fun getAllNotification(
        @AuthenticationPrincipal user: User,
        @PathVariable("generation") generation: Int,
    ): CommonResponse<List<NotificationRes.NotificationDto>> {
        return CommonResponse.onSuccess(notificationService.getAllNotification(generation))
    }


    @PostMapping("/all/{notificationId}")
    @Operation(summary = "02-03 공지 편집")
    fun upsertNotification(
        @AuthenticationPrincipal user: User,
        @PathVariable("notificationId") notificationId: Long,
        @RequestBody notificationInfo: NotificationReq.NotificationInfo
    ): CommonResponse<String> {
        return CommonResponse.onSuccess(
            notificationService.upsertNotification(
                notificationId = notificationId,
                notificationInfo = notificationInfo
            )
        )
    }

    @DeleteMapping("/{notificationId}")
    @Operation(summary = "02-04 공지 삭제")
    fun deleteNotification(
        @AuthenticationPrincipal user: User,
        @PathVariable("notificationId") notificationId: Long,
    ): CommonResponse<String> {
        return CommonResponse.onSuccess(
            notificationService.deleteNotification(notificationId = notificationId)
        )
    }

}