package com.example.cmc_be.utils

import org.springframework.stereotype.Service
import java.util.*

@Service
class RandomNumberUtil {

    private val rand = Random()

    fun createNumbers(
        length: Int = 6
    ): String {
        val numStr = StringBuilder()
        for (i in 1..length) {
            val ran = rand.nextInt(10).toString()
            numStr.append(ran)
        }
        return numStr.toString()
    }
}