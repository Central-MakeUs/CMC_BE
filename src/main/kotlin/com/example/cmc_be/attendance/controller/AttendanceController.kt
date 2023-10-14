package com.example.cmc_be.attendance.controller

import com.example.cmc_be.attendance.dto.AttendanceRes
import com.example.cmc_be.attendance.service.AttendanceService
import com.example.cmc_be.common.response.CommonResponse
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.notice.dto.NotificationRes
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/attendances")
@Tag(name ="03 Attendance 출석 관련 API")
class AttendanceController(
    private val attendanceService: AttendanceService
) {
    @GetMapping("")
    @Operation(summary = "03-01 출석 현황 조회")
    fun getAttendanceList(@AuthenticationPrincipal user: User) : CommonResponse<List<AttendanceRes.AttendanceInfoDto>> {
        return CommonResponse.onSuccess(attendanceService.getAttendanceList(user))
    }

    /*
    출석 하기
     */
}