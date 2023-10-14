package com.example.cmc_be.notice.service

import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.notice.dto.NotificationReq
import com.example.cmc_be.notice.dto.NotificationRes
import org.springframework.stereotype.Service

@Service
class NotificationService {
    fun getThisWeekNotification(user: User): List<NotificationRes.NotificationDto>? {
        TODO("Not yet implemented")
    }

    fun getAllNotification(user: User): List<NotificationRes.NotificationDto>? {
        TODO("Not yet implemented")
    }

    fun postNotification(notificationInfo: NotificationReq.NotificationInfo): NotificationRes.PostNotificationResponseDto? {
        TODO("Not yet implemented")
    }
}