package com.example.cmc_be.domain.notification.repository

import com.example.cmc_be.domain.notification.entity.Notification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<Notification, Long> {

    fun findAllByGenerationWeeksInfoGeneration(generation: Int, pageable: Pageable): Page<Notification>
    fun findAllByGenerationWeeksInfoGeneration(generation: Int, sort: Sort): List<Notification>
}
