package com.example.cmc_be.attendance.dto.res

data class AllAttendanceInfos(
    val name: String,
    val role: String,
    val nickname: String,
    val attendanceStatus: AttendanceDashboardInfo,
    val attandances: List<AttendanceInfo>
)