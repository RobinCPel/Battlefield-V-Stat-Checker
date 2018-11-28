package com.robinpel.battlefieldv_statchecker.core

import java.text.SimpleDateFormat
import java.util.*

object Logger {

    @JvmStatic private var dateTimeFormat:  String      = "[EEE dd MMM yyyy] [HH:mm:ss.SSS zzz]"
    @JvmStatic private var timeZone:        String      = "UTC"

    @JvmStatic private var regular:         Boolean     = true
    @JvmStatic private var error:           Boolean     = true
    @JvmStatic private var debug:           Boolean     = true


    @JvmStatic fun enableRegularLogging() { regular = true }
    @JvmStatic fun disableRegularLogging() { regular = false }

    @JvmStatic fun enableErrorLogging() { error = true }
    @JvmStatic fun disableErrorLogging() { error = false }

    @JvmStatic fun enableDebugLogging() { debug = true }
    @JvmStatic fun disableDebugLogging() { debug = false }

    @JvmStatic fun setDateTimeFormat(dateTimeFormat: String) { this.dateTimeFormat = dateTimeFormat }
    @JvmStatic fun setTimeZone(timeZone: String) { this.timeZone = timeZone }


    @JvmStatic fun log(caller: String, message: String) {
        if (!regular) return

        val parts = caller.split(".")
        val print: String = parts[parts.size-1]
        val timeStamp = getTimeStamp(dateTimeFormat, timeZone)
        System.out.println("$timeStamp [$print]:   \t$message")
    }

    @JvmStatic fun err(caller: String, message: String) {
        if (!error) return

        val parts = caller.split(".")
        val print: String = parts[parts.size-1]
        val timeStamp = getTimeStamp(dateTimeFormat, timeZone)
        System.err.println("$timeStamp [$print]:   \t$message")
    }

    @JvmStatic fun debug(caller: String, message: String) {
        if (!debug) return

        val parts = caller.split(".")
        val print: String = parts[parts.size-1]
        val timeStamp = getTimeStamp(dateTimeFormat, timeZone)
        System.out.println("$timeStamp [$print]:   \t(DEBUG) $message")
    }

    @JvmStatic private fun getTimeStamp(format: String, timeZone: String): String {
        val currentTime = Date()
        var sdf = SimpleDateFormat(format)
        sdf.timeZone = java.util.TimeZone.getTimeZone(timeZone)

        return sdf.format(currentTime)
    }

}