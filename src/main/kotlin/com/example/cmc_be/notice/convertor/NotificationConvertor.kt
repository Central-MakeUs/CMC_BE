package com.example.cmc_be.notice.convertor

import com.example.cmc_be.common.annotation.Convertor
import com.example.cmc_be.domain.notification.entity.Notification
import com.example.cmc_be.notice.dto.NotificationRes

@Convertor
class NotificationConvertor {

    fun getNotification(notification: Notification): NotificationRes.NotificationDto {
        return NotificationRes.NotificationDto(
            id = notification.id,
            title = notification.title,
            notionUrl = notification.notionUrl,
            week = notification.generationWeeksInfo.week
        )
    }
}