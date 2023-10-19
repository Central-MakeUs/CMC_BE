package com.example.cmc_be.notice.service

import com.example.cmc_be.common.exeption.NotFoundException
import com.example.cmc_be.common.response.PageResponse
import com.example.cmc_be.domain.generation.repository.GenerationWeeksInfoRepository
import com.example.cmc_be.domain.notification.exception.NotificationExceptionErrorCode
import com.example.cmc_be.domain.notification.repository.NotificationRepository
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.notice.convertor.NotificationConvertor
import com.example.cmc_be.notice.dto.NotificationReq
import com.example.cmc_be.notice.dto.NotificationRes
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val generationWeekRepository: GenerationWeeksInfoRepository,
    private val notificationConvertor: NotificationConvertor
) {
    fun getThisWeekNotification(user: User): NotificationRes.NotificationDto {
        val notification = notificationRepository.findAllByGenerationWeeksInfoGeneration(
            user.nowGeneration, PageRequest.of(0, 1, Sort.by("id").descending()),
        )
        return if (notification.isEmpty) {
            throw NotFoundException(NotificationExceptionErrorCode.NOT_FOUND_LATEST_NOTIFICATION)
        } else {
            notificationConvertor.getNotification(notification.first())
        }
    }

    fun getAllNotification(user: User): List<NotificationRes.NotificationDto> {
        return notificationRepository.findAllByGenerationWeeksInfoGeneration(
            generation = user.nowGeneration,
            sort = Sort.by("id").descending()
        ).map(notificationConvertor::getNotification)
    }

    fun getNotificationPaging(user: User, page: Int, size: Int): PageResponse<NotificationRes.NotificationDto> {
        val notificationPaging = notificationRepository.findAllByGenerationWeeksInfoGeneration(
            user.nowGeneration, PageRequest.of(page, size, Sort.by("id").descending()),
        )
        return PageResponse(
            isLast = notificationPaging.isLast,
            totalCnt = notificationRepository.count(),
            contents = notificationPaging.toList().map(notificationConvertor::getNotificationPaging)
        )
    }

    fun postNotification(notificationInfo: NotificationReq.NotificationInfo): String {
        val generationWeeksInfo =
            generationWeekRepository.findByGenerationAndWeek(notificationInfo.generation, notificationInfo.week)
                ?: throw NotFoundException(NotificationExceptionErrorCode.NOT_FOUND_GENERATION)
        return notificationRepository.save(
            notificationConvertor.postNotification(
                notificationInfo,
                generationWeeksInfo
            )
        ).let { "${notificationInfo.generation}기수 ${notificationInfo.week}주차 공지가 업데이트 되었습니다." }
    }
}