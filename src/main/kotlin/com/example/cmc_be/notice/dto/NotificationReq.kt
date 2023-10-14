package com.example.cmc_be.notice.dto

class NotificationReq {

    data class NotificationInfo(
        val generation: Int,
        val week: Int,
        val notionUrl: String,
        val title: String
    )
}