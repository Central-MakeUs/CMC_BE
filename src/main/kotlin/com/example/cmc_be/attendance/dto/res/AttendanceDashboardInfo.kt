package com.example.cmc_be.attendance.dto.res

import com.example.cmc_be.domain.attendance.enums.AttendanceCategory

data class AttendanceDashboardInfo(
    val attendanceCount: Int,
    val lateCount: Int,
    val absentCount: Int
) {

    companion object {
        fun from(attendanceInfos: List<AttendanceInfo>): AttendanceDashboardInfo {
            return AttendanceDashboardInfo(
                attendanceCount = attendanceInfos.count { it.firstHour == AttendanceCategory.ATTENDANCE } + attendanceInfos.count { it.secondHour == AttendanceCategory.ATTENDANCE },
                lateCount = attendanceInfos.count { it.firstHour == AttendanceCategory.LATE } + attendanceInfos.count { it.secondHour == AttendanceCategory.LATE },
                absentCount = attendanceInfos.count { it.firstHour == AttendanceCategory.ABSENT } + attendanceInfos.count { it.secondHour == AttendanceCategory.ABSENT },
            )
        }
    }
}
