package com.example.cmc_be.notice.dto

class NotificationRes {
    data class NotificationDto(
        val id: Long,
        val week: Int,
        val title: String,
        val notionUrl: String,
    )

}