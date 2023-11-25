package com.example.cmc_be.attendance.convertor

import com.example.cmc_be.attendance.dto.AttendanceRes
import com.example.cmc_be.common.annotation.Convertor
import com.example.cmc_be.domain.attendance.entity.Attendance
import com.example.cmc_be.domain.attendance.entity.AttendanceCode
import com.example.cmc_be.domain.attendance.enums.AttendanceCategory
import com.example.cmc_be.domain.attendance.enums.AttendanceHour
import com.example.cmc_be.domain.generation.entity.GenerationWeeksInfo
import com.example.cmc_be.domain.user.entity.User
import java.time.ZoneId
import java.time.ZonedDateTime

@Convertor
class AttendanceConverter {

    fun getAttendanceList(
        allGenerationWeeksInfo: List<GenerationWeeksInfo>,
        userAllAttendanceData: Map<Int, List<Attendance>>
    ): AttendanceRes.GetAttendances {
        val attendances = allGenerationWeeksInfo.map { generationWeekInfo ->
            val week = generationWeekInfo.week
            val firstHour =
                (userAllAttendanceData[week]?.find { it.attendanceHour == AttendanceHour.FIRST_HOUR })?.attendanceCategory
                    ?: AttendanceCategory.ABSENT
            val secondHour =
                (userAllAttendanceData[week]?.find { it.attendanceHour == AttendanceHour.SECOND_HOUR })?.attendanceCategory
                    ?: AttendanceCategory.ABSENT
            val currentDateTime = ZonedDateTime.now(ZoneId.systemDefault())
            val currentDate = currentDateTime.toLocalDate()

            AttendanceRes.AttendanceInfoDto(
                week = week,
                firstHour = firstHour,
                secondHour = secondHour,
                isOffline = generationWeekInfo.isOffline,
                enable = currentDate.isAfter(generationWeekInfo.date.minusDays(1L))
            )
        }
        val enableAttendances = attendances.filter { it.enable }
        return AttendanceRes.GetAttendances(
            attendanceStatus = AttendanceRes.AttendanceStatus(
                attendanceCount = enableAttendances.count { it.firstHour == AttendanceCategory.ATTENDANCE } + enableAttendances.count { it.secondHour == AttendanceCategory.ATTENDANCE },
                lateCount = enableAttendances.count { it.firstHour == AttendanceCategory.LATE } + enableAttendances.count { it.secondHour == AttendanceCategory.LATE },
                absentCount = enableAttendances.count { it.firstHour == AttendanceCategory.ABSENT } + enableAttendances.count { it.secondHour == AttendanceCategory.ABSENT },
            ),
            attandances = attendances
        )
    }

    fun getParticipantsAttendance(
        allUsers: List<User>,
        allAttendances: List<Attendance>,
        allGeneration: List<GenerationWeeksInfo>
    ) = allUsers.map { user ->
        val userAttandances = allAttendances.filter { it.user.id == user.id }
        val attendances = allGeneration.map { generationInfo ->
            val userAllAttendanceData =
                userAttandances.filter { it.generationWeeksInfo.week == generationInfo.week }
            val firstHour =
                (userAllAttendanceData.find { it.attendanceHour == AttendanceHour.FIRST_HOUR })?.attendanceCategory
                    ?: AttendanceCategory.ABSENT
            val secondHour =
                (userAllAttendanceData.find { it.attendanceHour == AttendanceHour.SECOND_HOUR })?.attendanceCategory
                    ?: AttendanceCategory.ABSENT
            AttendanceRes.AttendanceInfoDto(
                week = generationInfo.week,
                firstHour = firstHour,
                secondHour = secondHour,
                isOffline = generationInfo.isOffline,
                enable = true
            )
        }
        AttendanceRes.AllAttendanceInfoDto(
            name = user.name,
            role = user.role,
            nickname = user.nickname,
            attendanceStatus = AttendanceRes.AttendanceStatus(
                attendanceCount = attendances.count { it.firstHour == AttendanceCategory.ATTENDANCE } + attendances.count { it.secondHour == AttendanceCategory.ATTENDANCE },
                lateCount = attendances.count { it.firstHour == AttendanceCategory.LATE } + attendances.count { it.secondHour == AttendanceCategory.LATE },
                absentCount = attendances.count { it.firstHour == AttendanceCategory.ABSENT } + attendances.count { it.secondHour == AttendanceCategory.ABSENT },
            ),
            attandances = attendances
        )
    }

    fun getCodeInfo(codeInfo: AttendanceCode): AttendanceRes.AttendanceCodeDto {
        return AttendanceRes.AttendanceCodeDto(
            availableDate = codeInfo.generationWeeksInfo.date,
            startTime = codeInfo.startTime,
            endTime = codeInfo.endTime,
            lateMinute = codeInfo.lateMinute
        )
    }

}