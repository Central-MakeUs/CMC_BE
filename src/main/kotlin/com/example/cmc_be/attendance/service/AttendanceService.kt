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
import org.springframework.stereotype.Service

@Service
class AttendanceService(
    private val attendanceRepository: AttendanceRepository,
    private val generationWeeksInfoRepository: GenerationWeeksInfoRepository,
    private val attendanceConverter: AttendanceConverter
) {
    fun getAttendanceList(user: User): List<AttendanceRes.AttendanceInfoDto> {
        // 유저 현 기수의 모든 주차 데이터
        val generationWeeksInfos =
            generationWeeksInfoRepository.findAllByGeneration(user.nowGeneration).sortedBy { it.week }
        // 유저의 모든 출석 데이터
        val attendanceData = attendanceRepository.findAllByUserId(user.id).groupBy { it.generationWeeksInfo.week }
        return generationWeeksInfos.map { attendanceConverter.getAttendance(it.week, attendanceData) }
    }

    fun setAttendance(user: User, code: AttendanceReq.AttendanceCode): String {
        // TODO code parse
        val week = 2
        val attendanceHour = AttendanceHour.FIRST_HOUR

        if (attendanceRepository.findAllByUserId(user.id)
                .any { it.generationWeeksInfo.week == week && it.attendanceHour == attendanceHour && it.attendanceStatus == AttendanceStatus.ATTENDANCE }
        ) {
            throw BadRequestException(AttendanceErrorCode.ALREADY_ATEENDANCE)
        }

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

}