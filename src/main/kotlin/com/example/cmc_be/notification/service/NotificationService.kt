package com.example.cmc_be.notification.service

import com.example.cmc_be.common.exeption.NotFoundException
import com.example.cmc_be.common.response.PageResponse
import com.example.cmc_be.domain.generation.repository.GenerationWeeksInfoRepository
import com.example.cmc_be.domain.notification.entity.Notification
import com.example.cmc_be.domain.notification.exception.NotificationExceptionErrorCode
import com.example.cmc_be.domain.notification.repository.NotificationRepository
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.notification.dto.NotificationReq
import com.example.cmc_be.notification.dto.NotificationRes
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val generationWeekRepository: GenerationWeeksInfoRepository,
) {
    fun getLatestNotifications(user: User): List<NotificationRes> {
        val notification = notificationRepository.findAllByGenerationWeeksInfoGeneration(
            user.nowGeneration, PageRequest.of(0, 5, Sort.by("createdAt").descending()),
        )
        return if (notification.isEmpty) {
            throw NotFoundException(NotificationExceptionErrorCode.NOT_FOUND_LATEST_NOTIFICATION)
        } else {
            notification.take(5).map { notification -> NotificationRes.from(notification) }
        }
    }

    fun getAllNotifications(generation: Int): List<NotificationRes> {
        return notificationRepository.findAllByGenerationWeeksInfoGeneration(
            generation = generation,
            sort = Sort.by("createdAt").descending()
        ).map { notification -> NotificationRes.from(notification) }
    }

    fun getNotificationsPaging(user: User, page: Int, size: Int): PageResponse<NotificationRes> {
        val notificationPaging = notificationRepository.findAllByGenerationWeeksInfoGeneration(
            generation = user.nowGeneration,
            pageable = PageRequest.of(page, size, Sort.by("createdAt").descending()),
        )
        return PageResponse.from(
            page = notificationPaging,
            contentMapper = {
                NotificationRes.from(it)
            })
    }

    fun upsertNotification(notificationReq: NotificationReq, notificationId: Long? = null): Notification {
        val generationWeeksInfo =
            generationWeekRepository.findByGenerationAndWeek(notificationReq.generation, notificationReq.week)
                ?: throw NotFoundException(NotificationExceptionErrorCode.NOT_FOUND_GENERATION)
        return notificationRepository.save(
            Notification(
                generationWeeksInfo = generationWeeksInfo,
                title = notificationReq.title,
                notionUrl = notificationReq.notionUrl
            ).apply {
                if (notificationId != null) {
                    this.id = notificationId
                }
            }
        )
    }

    fun deleteNotification(notificationId: Long) {
        return notificationRepository.deleteById(notificationId)
    }
}