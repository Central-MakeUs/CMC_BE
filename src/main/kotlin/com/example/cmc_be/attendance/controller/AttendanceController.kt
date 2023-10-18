package com.example.cmc_be.attendance.controller

import com.example.cmc_be.attendance.dto.AttendanceReq
import com.example.cmc_be.attendance.dto.AttendanceRes
import com.example.cmc_be.attendance.service.AttendanceService
import com.example.cmc_be.common.response.CommonResponse
import com.example.cmc_be.domain.user.entity.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/attendances")
@Tag(name = "03 Attendance 출석 관련 API")
class AttendanceController(
    private val attendanceService: AttendanceService
) {
    @GetMapping("")
    @Operation(summary = "03-01 출석 현황 조회")
    fun getAttendanceList(@AuthenticationPrincipal user: User): CommonResponse<List<AttendanceRes.AttendanceInfoDto>> {
        return CommonResponse.onSuccess(attendanceService.getAttendanceList(user))
    }

    @PostMapping("")
    @Operation(summary = "03-02 출석 체크")
    fun setAttendance(
        @AuthenticationPrincipal user: User,
        @RequestBody attendanceCode: AttendanceReq.AttendanceCode
    ): CommonResponse<String> {
        return CommonResponse.onSuccess(attendanceService.setAttendance(user, attendanceCode))
    }

}