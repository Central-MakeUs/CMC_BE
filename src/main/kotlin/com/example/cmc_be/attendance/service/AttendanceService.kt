package com.example.cmc_be.attendance.service

import com.example.cmc_be.attendance.convertor.AttendanceConverter
import com.example.cmc_be.attendance.dto.AttendanceReq
import com.example.cmc_be.attendance.dto.AttendanceRes
import com.example.cmc_be.common.exeption.NotFoundException
import com.example.cmc_be.domain.attendance.entity.Attendance
import com.example.cmc_be.domain.attendance.enums.AttendanceStatus
import com.example.cmc_be.domain.attendance.repository.AttendanceRepository
import com.example.cmc_be.domain.generation.repository.GenerationWeeksInfoRepository
import com.example.cmc_be.domain.notification.exception.NotificationExceptionErrorCode
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AttendanceService(
    private val attendanceRepository: AttendanceRepository,
    private val generationWeeksInfoRepository: GenerationWeeksInfoRepository,
    private val userRepository: UserRepository,
    private val attendanceConverter: AttendanceConverter,
    private val qrCodeService: QrCodeService
) {
    fun getAttendanceList(user: User): List<AttendanceRes.AttendanceInfoDto> {
        val allGenerationWeeksInfo =
            generationWeeksInfoRepository.findAllByGeneration(user.nowGeneration).sortedBy { it.week }
        val userAllAttendanceData =
            attendanceRepository.findAllByUserId(user.id).groupBy { it.generationWeeksInfo.week }
        return attendanceConverter.getAttendanceList(allGenerationWeeksInfo, userAllAttendanceData)
    }

    fun setAttendance(user: User, code: AttendanceReq.AttendanceCode): String {
        val attendanceCode = qrCodeService.getCode(code.code)
        qrCodeService.validateCode(user, attendanceCode)
        val attendanceStatus = if (attendanceCode.isLate()) AttendanceStatus.LATE else AttendanceStatus.ATTENDANCE

        attendanceRepository.save(
            Attendance(
                user = user,
                generationWeeksInfo = generationWeeksInfoRepository.findByGenerationAndWeek(
                    user.nowGeneration,
                    attendanceCode.week
                ) ?: throw NotFoundException(NotificationExceptionErrorCode.NOT_FOUND_LATEST_NOTIFICATION),
                attendanceStatus = attendanceStatus,
                attendanceHour = attendanceCode.hour
            )
        )
        return "${attendanceStatus.status}하였습니다."
    }

    fun getParticipantsAttendance(generation: Int): List<AttendanceRes.AllAttendanceInfoDto> {
        val allGeneration = generationWeeksInfoRepository.findAllByGeneration(generation).sortedBy { it.week }
        val allUsers = userRepository.findAllByNowGeneration(generation)
        val allAttendances = attendanceRepository.findAllByGenerationWeeksInfoGeneration(generation)
        return attendanceConverter.getParticipantsAttendance(allUsers, allAttendances, allGeneration)
    }

}