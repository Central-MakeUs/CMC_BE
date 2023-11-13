package com.example.cmc_be.attendance.service

import com.example.cmc_be.common.exeption.BadRequestException
import com.example.cmc_be.domain.attendance.entity.AttendanceCode
import com.example.cmc_be.domain.attendance.exception.AttendanceErrorCode
import com.example.cmc_be.domain.attendance.repository.AttendanceRepository
import com.example.cmc_be.domain.user.entity.User
import org.springframework.stereotype.Component

@Component
class AttendanceCodeValidator(
    private val attendanceRepository: AttendanceRepository
) {

    fun validateAlreadyAttendance(
        userId: Long, attendanceCode: AttendanceCode
    ) {
        if (attendanceRepository.findAllByUserId(userId)
                .any { it.generationWeeksInfo.week == attendanceCode.week && it.attendanceHour == attendanceCode.hour }
        ) {
            throw BadRequestException(AttendanceErrorCode.ALREADY_ATEENDANCE)
        }
    }

    fun validateGeneration(user: User, generation: Int) {
        if (user.nowGeneration != generation) {
            throw BadRequestException(AttendanceErrorCode.CANNOT_ACCESS_ATEENDANCE)
        }
    }
}