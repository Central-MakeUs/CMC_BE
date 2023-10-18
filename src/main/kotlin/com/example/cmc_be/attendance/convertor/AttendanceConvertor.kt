package com.example.cmc_be.attendance.convertor

import com.example.cmc_be.attendance.dto.AttendanceRes
import com.example.cmc_be.common.annotation.Convertor
import com.example.cmc_be.domain.attendance.entity.Attendance
import com.example.cmc_be.domain.attendance.enums.AttendanceHour

@Convertor
class AttendanceConverter {
    fun getAttendance(
        week: Int,
        attendanceData: Map<Int, List<Attendance>>
    ): AttendanceRes.AttendanceInfoDto {
        return if (attendanceData[week] == null) {
            AttendanceRes.AttendanceInfoDto(
                week = week,
                firstHour = false,
                secondHour = false
            )
        } else {
            AttendanceRes.AttendanceInfoDto(
                week = week,
                firstHour = (attendanceData[week]?.any { it.attendanceHour == AttendanceHour.FIRST_HOUR }) == true,
                secondHour = (attendanceData[week]?.any { it.attendanceHour == AttendanceHour.SECOND_HOUR }) == true,
            )
        }
    }

}