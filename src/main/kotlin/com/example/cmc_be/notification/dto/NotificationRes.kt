package com.example.cmc_be.notification.dto

import com.example.cmc_be.domain.notification.entity.Notification

data class NotificationRes(
    val id: Long,
    val week: Int,
    val title: String,
    val notionUrl: String,
) {
    
    companion object {
        fun from(notification: Notification): NotificationRes {
            return NotificationRes(
                id = notification.id,
                title = notification.title,
                notionUrl = notification.notionUrl,
                week = notification.generationWeeksInfo.week
            )
        }
    }
}

