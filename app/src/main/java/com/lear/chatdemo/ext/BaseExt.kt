package com.lear.chatdemo.ext

import java.text.SimpleDateFormat
import java.util.*

fun Long.showAsTime(): String {
    val date = Date(this) // 使用时间戳（毫秒）构建时间
    val format = SimpleDateFormat("HH:mm:ss MM/dd")
    return format.format(date) // 2020-06-30 11:00:26.401
}

fun Boolean.runIfTrue(block: () -> Unit) {
    if (this) {
        block()
    }
}