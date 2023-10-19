package com.example.cmc_be.attendance.controller

import com.example.cmc_be.attendance.dto.AttendanceRes
import com.example.cmc_be.attendance.service.AttendanceService
import com.example.cmc_be.attendance.service.QrCodeService
import com.example.cmc_be.common.response.CommonResponse
import com.example.cmc_be.domain.user.entity.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/attendances")
@Tag(name = "ADMIN-03 Attendance 출석 관련 API")
class AdminAttendanceController(
    private val qrCodeService: QrCodeService,
    private val attendanceService: AttendanceService
) {
    @GetMapping("/code")
    @Operation(summary = "03-01 출석용 코드 생성(중복 생성 가능)")
    fun getAttendanceList(
        @AuthenticationPrincipal user: User,
        @Parameter(description = "기수", example = "13") @RequestParam generation: Int,
        @Parameter(description = "주차", example = "1") @RequestParam week: Int,
        @Parameter(description = "출석 차례(첫 번째 출석 : 1, 두 번째 출석 : 2)", example = "1 or 2") @RequestParam hour: Int,
    ): CommonResponse<String> {
        return CommonResponse.onSuccess(qrCodeService.generateCode(generation, week, hour))
    }

    @GetMapping("/all")
    @Operation(summary = "03-02 기수별 모든 출석 현황 체크")
    fun getParticipantsAttendance(
        @AuthenticationPrincipal user: User,
        @Parameter(description = "기수", example = "13") @RequestParam generation: Int,
    ): CommonResponse<List<AttendanceRes.AllAttendanceInfoDto>> {
        return CommonResponse.onSuccess(attendanceService.getParticipantsAttendance(generation))
    }

}