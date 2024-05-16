package com.example.cmc_be.common.utils

import java.util.*

object RandomNumberUtil {
    fun createNumbers(
        length: Int = 6
    ): String {
        val rand = Random()
        var numStr: String = ""
        for (i in 1..length) {
            val ran = rand.nextInt(10).toString()
            numStr += ran
        }
        return numStr
    }
}