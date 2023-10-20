package com.example.cmc_be.domain.attendance.repository

import com.example.cmc_be.domain.attendance.entity.AttendanceCode
import org.springframework.data.jpa.repository.JpaRepository

interface AttendanceCodeRepository : JpaRepository<AttendanceCode, String>