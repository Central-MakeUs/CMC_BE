package com.example.cmc_be.attendance.convertor

import com.example.cmc_be.attendance.dto.AttendanceRes
import com.example.cmc_be.common.annotation.Convertor
import com.example.cmc_be.domain.attendance.entity.Attendance
import com.example.cmc_be.domain.attendance.enums.AttendanceHour
import com.example.cmc_be.domain.generation.entity.GenerationWeeksInfo
import com.example.cmc_be.domain.user.entity.User

@Convertor
class AttendanceConverter {

    fun getAttendanceList(
        allGenerationWeeksInfo: List<GenerationWeeksInfo>,
        allAttendanceData: Map<Int, List<Attendance>>
    ) = allGenerationWeeksInfo.map {
        val week = it.week
        if (allAttendanceData[week] == null) {
            AttendanceRes.AttendanceInfoDto(
                week = week,
                firstHour = false,
                secondHour = false
            )
        } else {
            AttendanceRes.AttendanceInfoDto(
                week = week,
                firstHour = (allAttendanceData[week]?.any { it.attendanceHour == AttendanceHour.FIRST_HOUR }) == true,
                secondHour = (allAttendanceData[week]?.any { it.attendanceHour == AttendanceHour.SECOND_HOUR }) == true,
            )
        }
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
                val userWeekAttendances =
                    userAttandances.filter { it.generationWeeksInfo.week == generationInfo.week }
                AttendanceRes.AttendanceInfoDto(
                    week = generationInfo.week,
                    firstHour = userWeekAttendances.any { it.attendanceHour == AttendanceHour.FIRST_HOUR },
                    secondHour = userWeekAttendances.any { it.attendanceHour == AttendanceHour.SECOND_HOUR }
                )
            }
        )
    }

}