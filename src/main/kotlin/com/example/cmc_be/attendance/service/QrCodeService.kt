package com.example.cmc_be.attendance.service

import com.example.cmc_be.attendance.dto.AttendanceReq
import com.example.cmc_be.common.exeption.NotFoundException
import com.example.cmc_be.domain.attendance.entity.AttendanceCode
import com.example.cmc_be.domain.attendance.enums.AttendanceHour
import com.example.cmc_be.domain.attendance.exception.AttendanceErrorCode
import com.example.cmc_be.domain.attendance.repository.AttendanceCodeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class QrCodeService(
    private val attendanceCodeRepository: AttendanceCodeRepository
) {

    fun generateCode(generationReq: AttendanceReq.GenerateCode): String {
        return generateUniqueRandomCode().also { randomCode ->
            attendanceCodeRepository.save(
                AttendanceCode(
                    id = randomCode,
                    generation = generationReq.generation,
                    week = generationReq.week,
                    hour = AttendanceHour.of(generationReq.hour),
                    startTime = generationReq.startTime.toLocalTime(),
                    availableDate = generationReq.availableDate,
                    endTime = generationReq.endTime.toLocalTime(),
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

    /**
     * @return Triple<기수, 주차, 출석 차례>
     */
    fun parseCode(code: String): Triple<Int, Int, AttendanceHour> {
        val attendanceCode = attendanceCodeRepository.findByIdOrNull(code)
            ?: throw NotFoundException(AttendanceErrorCode.NOT_EXIST_ATTENDANCE_CODE)
        attendanceCode.validate()
        return Triple(attendanceCode.generation, attendanceCode.week, attendanceCode.hour)
    }


    companion object {
        private const val CODE_CHARACTERS = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        private const val CODE_LENGTH = 7
        private val RANDOM_INSTANT = Random.Default
    }
}