package com.example.cmc_be

import jakarta.xml.bind.DatatypeConverter
import org.junit.jupiter.api.Test
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

class HashingTest {

    @Test
    fun generateCode() {
        val generation = "13"
        val week = "10"
        val hour = "1"
        val key = "testKey"
        val code = generation + addSalt() + week + addSalt() + hour
        println(code)
        val compressCode = xorEncryptDecrypt(code, key)
        println("compress : ${compressCode}")
        val deCompressCode = xorEncryptDecrypt(code, key)
        println("decompress : $deCompressCode")
        assert(compressCode == deCompressCode)
    }

    fun addSalt(): String {
        val salts = listOf("/", "-", ".", "_")
        val salt = StringBuilder()
        repeat(Random.nextInt(1, 3)) {
            salt.append(salts.random())
        }
        return salt.toString()
    }

    fun xorEncryptDecrypt(input: String, key: String): String {
        val encrypted = input.mapIndexed { index, char ->
            (char.code xor key[index % key.length].code).toChar()
        }.joinToString("")
        return encrypted
    }

    fun generateKey(): SecretKey {
        return SecretKeySpec("keyValuekeyValue".toByteArray(), "AES")
    }

    fun encryptText(text: String, secretKey: SecretKey, ivSpec: IvParameterSpec): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)
        val paddedText = text.padEnd(12, ' ')
        val encryptedBytes = cipher.doFinal(paddedText.toByteArray())
        return DatatypeConverter.printHexBinary(encryptedBytes)
    }

    fun decryptText(encryptedText: String, secretKey: SecretKey, ivSpec: IvParameterSpec): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)
        val encryptedBytes = DatatypeConverter.parseHexBinary(encryptedText)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }

}