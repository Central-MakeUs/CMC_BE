package com.example.cmc_be.domain.attendance.repository

import com.example.cmc_be.domain.attendance.entity.Attendance
import org.springframework.data.jpa.repository.JpaRepository

interface AttendanceRepository : JpaRepository<Attendance, Long> {
}