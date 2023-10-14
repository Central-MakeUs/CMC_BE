package com.example.cmc_be.notice.service

import com.example.cmc_be.common.exeption.NotFoundException
import com.example.cmc_be.domain.generation.repository.GenerationWeeksInfoRepository
import com.example.cmc_be.domain.notification.exception.NotificationExceptionErrorCode
import com.example.cmc_be.domain.notification.repository.NotificationRepository
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.notice.convertor.NotificationConvertor
import com.example.cmc_be.notice.dto.NotificationReq
import com.example.cmc_be.notice.dto.NotificationRes
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val generationWeekRepository: GenerationWeeksInfoRepository,
    private val notificationConvertor: NotificationConvertor
) {
    fun getThisWeekNotification(user: User): NotificationRes.NotificationDto {
        val generationWeeksInfo = generationWeekRepository.findAllByGeneration(user.nowGeneration)
        val notification = notificationRepository.findTopByGenerationWeeksInfoInOrderById(generationWeeksInfo)
            ?: throw NotFoundException(NotificationExceptionErrorCode.NOT_FOUND_LATEST_NOTIFICATION)

        return if (
            !(LocalDate.now().isAfter(notification.generationWeeksInfo.weekEnd))
            && !(LocalDate.now().isBefore(notification.generationWeeksInfo.weekStart))
        ) {
            notificationConvertor.getNotification(notification)
        } else {
            throw NotFoundException(NotificationExceptionErrorCode.NOT_FOUND_LATEST_NOTIFICATION)
        }
    }

    fun getAllNotification(user: User): List<NotificationRes.NotificationDto> {
        return generationWeekRepository.findAllByGeneration(user.nowGeneration)
            .let { notificationRepository.findAllByGenerationWeeksInfoIn(it) }
            .sortedByDescending { it.id }
            .map(notificationConvertor::getNotification)
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