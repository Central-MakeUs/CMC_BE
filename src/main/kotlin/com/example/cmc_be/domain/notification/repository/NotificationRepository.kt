package com.example.cmc_be.domain.notification.repository

import com.example.cmc_be.domain.generation.entity.GenerationWeeksInfo
import com.example.cmc_be.domain.notification.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<Notification, Long> {

    fun findAllByGenerationWeeksInfoIn(generationWeeksInfo: List<GenerationWeeksInfo>): List<Notification>
    fun findTopByGenerationWeeksInfoInOrderById(generationWeeksInfo: List<GenerationWeeksInfo>): Notification?
}
