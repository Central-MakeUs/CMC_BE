package com.example.cmc_be.attendance.convertor

import com.example.cmc_be.attendance.dto.AttendanceRes
import com.example.cmc_be.common.annotation.Convertor
import com.example.cmc_be.domain.attendance.entity.Attendance
import com.example.cmc_be.domain.attendance.entity.AttendanceCode
import com.example.cmc_be.domain.attendance.enums.AttendanceHour
import com.example.cmc_be.domain.attendance.enums.AttendanceStatus
import com.example.cmc_be.domain.generation.entity.GenerationWeeksInfo
import com.example.cmc_be.domain.user.entity.User

@Convertor
class AttendanceConverter {

    fun getAttendanceList(
        allGenerationWeeksInfo: List<GenerationWeeksInfo>,
        userAllAttendanceData: Map<Int, List<Attendance>>
    ) = allGenerationWeeksInfo.map {
        val week = it.week
        val firstHour =
            (userAllAttendanceData[week]?.find { it.attendanceHour == AttendanceHour.FIRST_HOUR })?.attendanceStatus
                ?: AttendanceStatus.ABSENT
        val secondHour =
            (userAllAttendanceData[week]?.find { it.attendanceHour == AttendanceHour.SECOND_HOUR })?.attendanceStatus
                ?: AttendanceStatus.ABSENT
        AttendanceRes.AttendanceInfoDto(
            week = week,
            firstHour = firstHour,
            secondHour = secondHour,
        )
    }

    fun getParticipantsAttendance(
        allUsers: List<User>,
        allAttendances: List<Attendance>,
        allGeneration: List<GenerationWeeksInfo>
    ) = allUsers.map { user ->
        val userAttandances = allAttendances.filter { it.user.id == user.id }
        AttendanceRes.AllAttendanceInfoDto(
            name = user.name,
            role = user.role,
            nickname = user.nickname,
            attandances = allGeneration.map { generationInfo ->
                val userAllAttendanceData =
                    userAttandances.filter { it.generationWeeksInfo.week == generationInfo.week }
                val firstHour =
                    (userAllAttendanceData.find { it.attendanceHour == AttendanceHour.FIRST_HOUR })?.attendanceStatus
                        ?: AttendanceStatus.ABSENT
                val secondHour =
                    (userAllAttendanceData.find { it.attendanceHour == AttendanceHour.SECOND_HOUR })?.attendanceStatus
                        ?: AttendanceStatus.ABSENT
                AttendanceRes.AttendanceInfoDto(
                    week = generationInfo.week,
                    firstHour = firstHour,
                    secondHour = secondHour
                )
            }
        )
    }

    fun getCodeInfo(codeInfo: AttendanceCode): AttendanceRes.AttendanceCodeDto {
        return AttendanceRes.AttendanceCodeDto(
            availableDate = codeInfo.availableDate,
            startTime = codeInfo.startTime,
            endTime = codeInfo.endTime,
            lateMinute = codeInfo.lateMinute
        )
    }

}