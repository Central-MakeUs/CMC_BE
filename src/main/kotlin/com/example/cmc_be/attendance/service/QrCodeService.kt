package com.example.cmc_be.attendance.service

import com.example.cmc_be.attendance.convertor.AttendanceConverter
import com.example.cmc_be.attendance.dto.AttendanceReq
import com.example.cmc_be.attendance.dto.AttendanceRes
import com.example.cmc_be.common.exeption.BadRequestException
import com.example.cmc_be.common.exeption.NotFoundException
import com.example.cmc_be.domain.attendance.entity.AttendanceCode
import com.example.cmc_be.domain.attendance.enums.AttendanceHour
import com.example.cmc_be.domain.attendance.exception.AttendanceErrorCode
import com.example.cmc_be.domain.attendance.repository.AttendanceCodeRepository
import com.example.cmc_be.domain.generation.entity.GenerationWeeksInfo
import com.example.cmc_be.domain.user.entity.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class QrCodeService(
    private val attendanceCodeRepository: AttendanceCodeRepository,
    private val attendanceCodeValidator: AttendanceCodeValidator,
    private val attendanceCodeConverter: AttendanceConverter
) {

    fun generateCode(
        generationReq: AttendanceReq.GenerateCode,
        generationWeeksInfo: GenerationWeeksInfo
    ): String {
        return generateUniqueRandomCode().also { randomCode ->
            attendanceCodeRepository.save(
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
        }
    }

    private fun generateUniqueRandomCode(): String {
        var randomCode: String
        do {
            randomCode = generateRandomCode()
        } while (attendanceCodeRepository.findByIdOrNull(randomCode) != null)
        return randomCode
    }

    private fun generateRandomCode(): String {
        return (1..CODE_LENGTH)
            .map { CODE_CHARACTERS[RANDOM_INSTANT.nextInt(0, CODE_CHARACTERS.length)] }
            .joinToString("")
    }

    fun getCode(code: String): AttendanceCode {
        return attendanceCodeRepository.findByIdOrNull(code)
            ?: throw NotFoundException(AttendanceErrorCode.NOT_EXIST_ATTENDANCE_CODE)
    }

    @Scheduled(cron = "0 0 2 * * *")
    fun deleteObjectsOutsideTimeRange() {
        val attendanceCodes = attendanceCodeRepository.findAll()
        val invalideAttendanceCodes = attendanceCodes.filter { attendanceCode -> attendanceCode.validate() != null }
        attendanceCodeRepository.deleteAll(invalideAttendanceCodes)
    }

    fun getCodeInfo(code: String): AttendanceRes.AttendanceCodeDto =
        attendanceCodeConverter.getCodeInfo(
            attendanceCodeRepository.findByIdOrNull(code) ?: throw BadRequestException(
                AttendanceErrorCode.INVALID_CODE
            )
        )

    fun validateCode(user: User, attendanceCode: AttendanceCode) {
        with(attendanceCodeValidator) {
            validateGeneration(user, attendanceCode.generation)
            validateAlreadyAttendance(user.id, attendanceCode)
        }
        val validateCodeException = attendanceCode.validate()
        if (validateCodeException != null) throw validateCodeException
    }


    companion object {
        private const val CODE_CHARACTERS = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        private const val CODE_LENGTH = 7
        private val RANDOM_INSTANT = Random.Default
    }
}