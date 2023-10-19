package com.example.cmc_be.attendance.service

import com.example.cmc_be.attendance.convertor.AttendanceConverter
import com.example.cmc_be.attendance.dto.AttendanceReq
import com.example.cmc_be.attendance.dto.AttendanceRes
import com.example.cmc_be.common.exeption.BadRequestException
import com.example.cmc_be.common.exeption.NotFoundException
import com.example.cmc_be.domain.attendance.entity.Attendance
import com.example.cmc_be.domain.attendance.enums.AttendanceHour
import com.example.cmc_be.domain.attendance.enums.AttendanceStatus
import com.example.cmc_be.domain.attendance.exception.AttendanceErrorCode
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
        val allAttendanceData = attendanceRepository.findAllByUserId(user.id).groupBy { it.generationWeeksInfo.week }
        return attendanceConverter.getAttendanceList(allGenerationWeeksInfo, allAttendanceData)
    }


    fun setAttendance(user: User, code: AttendanceReq.AttendanceCode): String {
        val (generation, week, hour) = qrCodeService.parseCode(code.code)
        val attendanceHour = AttendanceHour.of(hour)

        validateGeneration(user, generation)
        validateAlreadyAttendance(user.id, week, attendanceHour)

        attendanceRepository.save(
            Attendance(
                user = user,
                generationWeeksInfo = generationWeeksInfoRepository.findByGenerationAndWeek(user.nowGeneration, week)
                    ?: throw NotFoundException(NotificationExceptionErrorCode.NOT_FOUND_LATEST_NOTIFICATION),
                attendanceStatus = AttendanceStatus.ATTENDANCE,
                attendanceHour = attendanceHour
            )
        )
        return "출석에 성공하였습니다."
    }

    fun getParticipantsAttendance(generation: Int): List<AttendanceRes.AllAttendanceInfoDto> {
        val allGeneration = generationWeeksInfoRepository.findAllByGeneration(generation).sortedBy { it.week }
        val allUsers = userRepository.findAllByNowGeneration(generation)
        val allAttendances = attendanceRepository.findAllByGenerationWeeksInfoGeneration(generation)
        return attendanceConverter.getParticipantsAttendance(allUsers, allAttendances, allGeneration)
    }

    private fun validateAlreadyAttendance(
        userId: Long, week: Int, attendanceHour: AttendanceHour
    ) {
        if (attendanceRepository.findAllByUserId(userId)
                .any { it.generationWeeksInfo.week == week && it.attendanceHour == attendanceHour && it.attendanceStatus == AttendanceStatus.ATTENDANCE }
        ) {
            throw BadRequestException(AttendanceErrorCode.ALREADY_ATEENDANCE)
        }
    }

    private fun validateGeneration(user: User, generation: Int) {
        if (user.nowGeneration != generation) {
            throw BadRequestException(AttendanceErrorCode.CANNOT_ACCESS_ATEENDANCE)
        }
    }

}