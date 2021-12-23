package com.lear.chatdemo.ext

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.gson.Gson
import com.lear.chatdemo.App
import com.lear.chatdemo.activity.ui.chat.model.Message
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

fun String.asMessage(): Message? {
    try {
        return Gson().fromJson(this, Message::class.java)
    } catch (e: Exception) {
        Log.e(App.TAG, "parsing message failed: ${e.localizedMessage}")
    }
    return null
}

@SuppressLint("MissingPermission")
fun Activity.getSN(): String {
    var androidId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        .toUpperCase()
    var androidIdLength = androidId.length
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                1
            )
            ""
        } else {
            if (TextUtils.isEmpty(Build.getSerial()) || TextUtils.equals(
                    Build.getSerial(),
                    "unknown"
                )
            ) {
                androidId.substring(androidIdLength - 6)
            } else {
                Build.getSerial()
            }
        }
    } else {
        Build.SERIAL
    }
}