package com.example.cmc_be.attendance.service

import com.example.cmc_be.attendance.dto.AttendanceRes
import com.example.cmc_be.domain.user.entity.User
import org.springframework.stereotype.Service

@Service
class AttendanceService {
    fun getAttendanceList(user: User): List<AttendanceRes.AttendanceInfoDto>? {
        TODO("Not yet implemented")
    }
}