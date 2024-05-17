package com.example.cmc_be.attendance.dto.res

data class AttendancesDashboard(
    val attendanceStatus: AttendanceDashboardInfo,
    val attandances: List<AttendanceInfo>
) {

    companion object {
        fun from(attendanceInfos: List<AttendanceInfo>): AttendancesDashboard {
            val enableAttendances = attendanceInfos.filter { it.enable }
            return AttendancesDashboard(
                attendanceStatus = AttendanceDashboardInfo.from(enableAttendances),
                attandances = attendanceInfos
            )
        }
    }
}