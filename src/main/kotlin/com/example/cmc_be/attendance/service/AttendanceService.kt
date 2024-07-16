package com.example.cmc_be.attendance.service

import com.example.cmc_be.attendance.dto.req.AttendanceCodeReq
import com.example.cmc_be.attendance.dto.res.AllAttendanceInfos
import com.example.cmc_be.attendance.dto.res.AttendanceDashboardInfo
import com.example.cmc_be.attendance.dto.res.AttendanceInfo
import com.example.cmc_be.attendance.dto.res.AttendancesDashboard
import com.example.cmc_be.common.dto.response.PageResponse
import com.example.cmc_be.common.exeption.NotFoundException
import com.example.cmc_be.domain.attendance.entity.Attendance
import com.example.cmc_be.domain.attendance.enums.AttendanceCategory
import com.example.cmc_be.domain.attendance.enums.AttendanceHour
import com.example.cmc_be.domain.attendance.repository.AttendanceRepository
import com.example.cmc_be.domain.generation.repository.GenerationWeeksInfoRepository
import com.example.cmc_be.domain.notification.exception.NotificationExceptionErrorCode
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.domain.user.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class AttendanceService(
    private val attendanceRepository: AttendanceRepository,
    private val generationWeeksInfoRepository: GenerationWeeksInfoRepository,
    private val userRepository: UserRepository,
    private val qrCodeService: QrCodeService
) {
    fun getAttendanceList(user: User): AttendancesDashboard {
        val allGenerationWeeksInfo =
            generationWeeksInfoRepository.findAllByGeneration(user.nowGeneration).sortedBy { it.week }
        val userAllAttendanceData =
            attendanceRepository.findAllByUserId(user.id).groupBy { it.generationWeeksInfo.week }

        val attendanceInfos = allGenerationWeeksInfo.map { generationWeekInfo ->
            val week = generationWeekInfo.week
            val firstHour =
                (userAllAttendanceData[week]?.find { it.attendanceHour == AttendanceHour.FIRST_HOUR })?.attendanceCategory
                    ?: AttendanceCategory.ABSENT
            val secondHour =
                (userAllAttendanceData[week]?.find { it.attendanceHour == AttendanceHour.SECOND_HOUR })?.attendanceCategory
                    ?: AttendanceCategory.ABSENT
            val currentDateTime = ZonedDateTime.now(ZoneId.systemDefault())
            val currentDate = currentDateTime.toLocalDate()

            AttendanceInfo(
                week = week,
                firstHour = firstHour,
                secondHour = secondHour,
                isOffline = generationWeekInfo.isOffline,
                date = generationWeekInfo.date,
                enable = currentDate.isAfter(generationWeekInfo.date.minusDays(1L))
            )
        }
        return AttendancesDashboard.from(attendanceInfos)
    }

    fun setAttendance(user: User, code: AttendanceCodeReq): String {
        val attendanceCode = qrCodeService.getCode(code.code)
        qrCodeService.validateCode(user, attendanceCode)
        val attendanceCategory = if (attendanceCode.isLate()) AttendanceCategory.LATE else AttendanceCategory.ATTENDANCE

        attendanceRepository.save(
            Attendance(
                user = user,
                generationWeeksInfo = generationWeeksInfoRepository.findByGenerationAndWeek(
                    user.nowGeneration,
                    attendanceCode.week
                ) ?: throw NotFoundException(NotificationExceptionErrorCode.NOT_FOUND_LATEST_NOTIFICATION),
                attendanceCategory = attendanceCategory,
                attendanceHour = attendanceCode.hour
            )
        )
        return "${attendanceCategory.status}하였습니다."
    }

    fun getParticipantsAttendance(generation: Int, page: Int, size: Int): PageResponse<AllAttendanceInfos> {
        val pageable = PageRequest.of(page, size, Sort.by("id").descending())
        val allGeneration = generationWeeksInfoRepository.findAllByGeneration(generation).sortedBy { it.week }
        val allUsers = userRepository.findAllByNowGeneration(generation, pageable)
        val allAttendances = attendanceRepository.findAllByGenerationWeeksInfoGeneration(generation)
        return PageResponse.from(allUsers.map { user ->
            val userAttandances = allAttendances.filter { it.user.id == user.id }
            val attendanceInfos = allGeneration.map { generationWeekInfo ->
                val userAllAttendanceData =
                    userAttandances.filter { it.generationWeeksInfo.week == generationWeekInfo.week }
                val firstHour =
                    (userAllAttendanceData.find { it.attendanceHour == AttendanceHour.FIRST_HOUR })?.attendanceCategory
                        ?: AttendanceCategory.ABSENT
                val secondHour =
                    (userAllAttendanceData.find { it.attendanceHour == AttendanceHour.SECOND_HOUR })?.attendanceCategory
                        ?: AttendanceCategory.ABSENT
                AttendanceInfo(
                    week = generationWeekInfo.week,
                    firstHour = firstHour,
                    secondHour = secondHour,
                    isOffline = generationWeekInfo.isOffline,
                    date = generationWeekInfo.date,
                    enable = true,
                )
            }
            AllAttendanceInfos(
                name = user.name,
                role = user.role,
                nickname = user.nickname,
                attendanceStatus = AttendanceDashboardInfo.from(attendanceInfos),
                attandances = attendanceInfos
            )
        })
    }
}