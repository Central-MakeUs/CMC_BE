package com.example.cmc_be.notice.convertor

import com.example.cmc_be.common.annotation.Convertor
import com.example.cmc_be.domain.generation.entity.GenerationWeeksInfo
import com.example.cmc_be.domain.notification.entity.Notification
import com.example.cmc_be.notice.dto.NotificationReq
import com.example.cmc_be.notice.dto.NotificationRes

@Convertor
class NotificationConvertor {

    fun getNotification(notification: Notification): NotificationRes.NotificationDto {
        return NotificationRes.NotificationDto(
            title = notification.title,
            notionUrl = notification.notionUrl,
            week = notification.generationWeeksInfo.week
        )
    }

    fun postNotification(
        notificationInfo: NotificationReq.NotificationInfo,
        generationWeeksInfo: GenerationWeeksInfo
    ): Notification {
        return Notification(
            generationWeeksInfo = generationWeeksInfo,
            title = notificationInfo.title,
            notionUrl = notificationInfo.notionUrl
        )
    }
}