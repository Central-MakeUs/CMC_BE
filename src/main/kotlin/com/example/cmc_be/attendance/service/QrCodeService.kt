package com.example.cmc_be.attendance.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class QrCodeService(
    @Value("\${qr.key}")
    private val qrSecretKey: String
) {

    fun generateCode(generation: Int, week: Int, hour: Int): String =
        xorEncryptDecrypt(
            input = generation.toString() + addSalt() + week.toString() + addSalt() + hour.toString(),
            key = qrSecretKey
        )

    private fun addSalt(): String {
        return StringBuilder().apply {
            repeat(Random.nextInt(MIN_SALT_LENGTH, MAX_SALT_LENGTH)) { append(salts.random()) }
        }.toString()
    }

    /**
     * @return Triple(기수, 주차, 출석차례(1 or 2))
     */
    fun parseCode(code: String): Triple<Int, Int, Int> {
        val result = xorEncryptDecrypt(code, key = qrSecretKey)
            .split(*salts.toTypedArray())
            .filter { it.isNotBlank() }
            .map { it.toInt() }
        return Triple(result[0], result[1], result[2])
    }

    private fun xorEncryptDecrypt(input: String, key: String): String {
        val encrypted = input.mapIndexed { index, char ->
            (char.code xor key[index % key.length].code).toChar()
        }.joinToString("")
        return encrypted
    }

    companion object {
        private val salts = listOf("/", "-", ".", "_")
        private val MIN_SALT_LENGTH = 1
        private val MAX_SALT_LENGTH = 3
    }
}