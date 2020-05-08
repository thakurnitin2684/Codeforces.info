package com.thakurnitin2684.codeforces

import java.text.SimpleDateFormat
import java.util.*

class TimeDate(milli: Int) {
    companion object {
        fun dateIs(milli: Int): String {
            val date = Date(milli * 1000L)
            val sdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z")
                sdf.timeZone = TimeZone.getTimeZone("GMT+5:30")
                return sdf.format(date)

        }
        fun dateIs2(milli: Int): String {
            val date = Date(milli * 1000L)
            val sdf = SimpleDateFormat("EEE, d MMM  HH:mm:ss")
            sdf.timeZone = TimeZone.getTimeZone("GMT+5:30")
            return sdf.format(date)

        }
    }
}