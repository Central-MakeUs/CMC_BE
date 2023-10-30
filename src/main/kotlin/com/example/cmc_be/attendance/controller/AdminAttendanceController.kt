package com.example.cmc_be.attendance.controller

import com.example.cmc_be.attendance.dto.AttendanceReq
import com.example.cmc_be.attendance.dto.AttendanceRes
import com.example.cmc_be.attendance.service.AttendanceService
import com.example.cmc_be.attendance.service.QrCodeService
import com.example.cmc_be.common.response.CommonResponse
import com.example.cmc_be.domain.user.entity.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/attendances")
@Tag(name = "ADMIN-03 Attendance 출석 관련 API")
class AdminAttendanceController(
    private val qrCodeService: QrCodeService,
    private val attendanceService: AttendanceService
) {
    @PostMapping("/code")
    @Operation(summary = "03-01 출석용 코드 생성(중복 생성 가능)")
    fun generateCode(
        @AuthenticationPrincipal user: User,
        @RequestBody gernerateCode: AttendanceReq.GenerateCode
    ): CommonResponse<String> {
        return CommonResponse.onSuccess(qrCodeService.generateCode(gernerateCode))
    }

    @GetMapping("/code")
    @Operation(summary = "03-02 코드 정보 조회")
    fun getCodeInfo(
        @AuthenticationPrincipal user: User,
        @Parameter(description = "코드", example = "8dFsb")
        @RequestParam code: String,
    ): CommonResponse<AttendanceRes.AttendanceCodeDto> {
        return CommonResponse.onSuccess(qrCodeService.getCodeInfo(code))
    }


    @GetMapping("/all")
    @Operation(summary = "03-03 기수별 모든 출석 현황 체크")
    fun getParticipantsAttendance(
        @AuthenticationPrincipal user: User,
        @Parameter(description = "기수", example = "13") @RequestParam generation: Int,
    ): CommonResponse<List<AttendanceRes.AllAttendanceInfoDto>> {
        return CommonResponse.onSuccess(attendanceService.getParticipantsAttendance(generation))
    }

}