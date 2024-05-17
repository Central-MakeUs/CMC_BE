package com.example.cmc_be.notification.dto

data class NotificationReq(
    val generation: Int,
    val week: Int,
    val notionUrl: String,
    val title: String
)
