package com.example.cmc_be.common.utils

import java.util.*

class RandomNumber {
    companion object {
        fun createRandomNumber(): String {
            val rand = Random()
            var numStr: String = ""
            for (i in 0..5) {
                val ran = rand.nextInt(10).toString()
                numStr += ran
            }
            return numStr
        }
    }
}