package com.example.cmc_be.domain.notification.repository

import com.example.cmc_be.domain.notification.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<Notification, Long> {
}