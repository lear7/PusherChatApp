package com.gkd.lib_chat.app.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun fromMillisToTimeString(millis: Long): String {
        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return format.format(millis)
    }
}