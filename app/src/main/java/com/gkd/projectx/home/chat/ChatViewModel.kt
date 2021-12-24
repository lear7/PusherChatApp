package com.gkd.projectx.home.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class ChatViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text

    private var uuid = 0

    // 随机大写字母+年份后两位+月份+小时+分+4位随机数
    fun getMsgId(): String {
        var first = (Random().nextInt(26) + 65).toChar().toString()
        var second = (Random().nextInt(26) + 65).toChar().toString()
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH) + 1
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        var minute = calendar.get(Calendar.MINUTE)

        if (uuid in (0..9998)) {
            uuid += 1
        } else if (uuid == 9999) {
            uuid = 0
        }
        var trans = String.format("%0" + 4 + "d", uuid)
        var mon = String.format("%0" + 2 + "d", month)
        var days = String.format("%0" + 2 + "d", day)
        var hou = String.format("%0" + 2 + "d", hour)
        var min = String.format("%0" + 2 + "d", minute)

        var str = StringBuffer("")
        str.append(first).append(second).append(year % 100).append(mon).append(days).append(hou)
            .append(min).append(trans)

        return str.toString()
    }
}