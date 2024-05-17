package com.example.cmc_be.attendance.service

import com.example.cmc_be.attendance.dto.req.GenerateCode
import com.example.cmc_be.attendance.dto.res.AttendanceCodeRes
import com.example.cmc_be.attendance.dto.res.QrSchemata
import com.example.cmc_be.common.exeption.BadRequestException
import com.example.cmc_be.common.exeption.NotFoundException
import com.example.cmc_be.domain.attendance.entity.AttendanceCode
import com.example.cmc_be.domain.attendance.enums.AttendanceHour
import com.example.cmc_be.domain.attendance.exception.AttendanceErrorCode
import com.example.cmc_be.domain.attendance.repository.AttendanceCodeRepository
import com.example.cmc_be.domain.attendance.repository.AttendanceRepository
import com.example.cmc_be.domain.generation.entity.GenerationWeeksInfo
import com.example.cmc_be.domain.user.entity.User
import com.example.cmc_be.utils.RandomNumberUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class QrCodeService(
    private val attendanceCodeRepository: AttendanceCodeRepository,
    private val attendanceRepository: AttendanceRepository,
    private val randomNumberUtil: RandomNumberUtil
) {

    fun generateCode(
        generationReq: GenerateCode,
        generationWeeksInfo: GenerationWeeksInfo
    ): QrSchemata {
        return generateUniqueRandomCode().let { randomCode ->
            val attendanceCode = attendanceCodeRepository.save(
                AttendanceCode(
                    id = randomCode,
                    generation = generationReq.generation,
                    week = generationReq.week,
                    hour = AttendanceHour.of(generationReq.hour),
                    startTime = generationReq.startTime.toLocalTime(),
                    endTime = generationReq.endTime.toLocalTime(),
                    generationWeeksInfo = generationWeeksInfo,
                    lateMinute = generationReq.lateMinute
                )
            )
            QrSchemata(
                androidSchema = generateAndroidSchmea(randomCode),
                iosSchema = generateIOSSchmea(randomCode),
                attendanceCode = attendanceCode
            )
        }
    }

    private fun generateUniqueRandomCode(): String {
        var randomCode: String
        do {
            randomCode = randomNumberUtil.createNumbers(6)
        } while (attendanceCodeRepository.findByIdOrNull(randomCode) != null)
        return randomCode
    }

    fun getCode(code: String): AttendanceCode {
        return attendanceCodeRepository.findByIdOrNull(code)
            ?: throw NotFoundException(AttendanceErrorCode.NOT_EXIST_ATTENDANCE_CODE)
    }

    @Scheduled(cron = "0 0 2 * * *")
    fun deleteAttendanceCodesOutOfTime() {
        val attendanceCodes = attendanceCodeRepository.findAll()
        val invalidAttendanceCodes = attendanceCodes.filter { attendanceCode -> attendanceCode.validate() != null }
        attendanceCodeRepository.deleteAll(invalidAttendanceCodes)
    }

    fun getAllCodes(): List<AttendanceCode> {
        return attendanceCodeRepository.findAll()
    }

    fun getCodeInfo(code: String): AttendanceCodeRes {
        val codeInfo = attendanceCodeRepository.findByIdOrNull(code) ?: throw BadRequestException(
            AttendanceErrorCode.INVALID_CODE
        )
        return AttendanceCodeRes(
            availableDate = codeInfo.generationWeeksInfo.date,
            startTime = codeInfo.startTime,
            endTime = codeInfo.endTime,
            lateMinute = codeInfo.lateMinute
        )
    }

    fun validateCode(user: User, attendanceCode: AttendanceCode) {
        validateGeneration(user, attendanceCode.generation)
        validateAlreadyAttendance(user.id, attendanceCode)
        attendanceCode.validate()?.let { throw it }
    }

    private fun validateAlreadyAttendance(userId: Long, attendanceCode: AttendanceCode) {
        if (attendanceRepository.findAllByUserId(userId)
                .any { it.generationWeeksInfo.week == attendanceCode.week && it.attendanceHour == attendanceCode.hour }
        ) {
            throw BadRequestException(AttendanceErrorCode.ALREADY_ATEENDANCE)
        }
    }

    private fun validateGeneration(user: User, generation: Int) {
        if (user.nowGeneration != generation) {
            throw BadRequestException(AttendanceErrorCode.CANNOT_ACCESS_ATEENDANCE)
        }
    }

    private fun generateIOSSchmea(code: String): String {
        return "cmcqrcodechecker://path?code=$code"
    }

    private fun generateAndroidSchmea(code: String): String {
        return "com.cmc.android://attendance&code=$code"
    }

}