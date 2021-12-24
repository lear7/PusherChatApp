package com.gkd.projectx.ext

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gkd.data.common.CallErrors
import com.gkd.projectx.App
import com.gkd.projectx.R
import com.gkd.data.model.Message
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KClass

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

/**
 * Created by Rim Gazzah on 8/31/20.
 **/
fun <T : ViewModel> AppCompatActivity.viewModelProvider(
    factory: ViewModelProvider.Factory,
    model: KClass<T>
): T {
    return ViewModelProvider(this, factory).get(model.java)
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

fun CallErrors.getMessage(context: Context): String {
    return when (this) {
        is CallErrors.ErrorEmptyData -> context.getString(R.string.error_empty_data)
        is CallErrors.ErrorServer -> context.getString(R.string.error_server_error)
        is CallErrors.ErrorException -> context.getString(
            R.string.error_exception
        )
    }
}