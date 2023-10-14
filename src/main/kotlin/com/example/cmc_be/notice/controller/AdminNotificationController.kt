package com.example.cmc_be.notice.controller

import com.example.cmc_be.common.response.CommonResponse
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.notice.dto.NotificationReq
import com.example.cmc_be.notice.dto.NotificationRes
import com.example.cmc_be.notice.service.NotificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/notifications")
@Tag(name ="ADMIN-02 Notification 공지 관련 API")
class AdminNotificationController(
    private val notificationService: NotificationService
) {

    @PostMapping("")
    @Operation(summary = "02-01 공지 업로드")
    fun postNotification(@RequestBody notificationInfo : NotificationReq.NotificationInfo) : CommonResponse<NotificationRes.PostNotificationResponseDto>{
        return CommonResponse.onSuccess(notificationService.postNotification(notificationInfo))
    }

}