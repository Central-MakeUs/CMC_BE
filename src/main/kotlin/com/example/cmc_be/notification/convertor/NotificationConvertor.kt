package com.example.cmc_be.notification.convertor

import com.example.cmc_be.common.annotation.Convertor
import com.example.cmc_be.domain.notification.entity.Notification
import com.example.cmc_be.notification.dto.NotificationRes

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