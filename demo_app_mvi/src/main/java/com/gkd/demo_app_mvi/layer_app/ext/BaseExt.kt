package com.gkd.demo_app_mvi.layer_app.ext

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gkd.demo_app_mvi.R
import com.gkd.demo_app_mvi.layer_data.common.CallErrors
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

fun CallErrors.getMessage(context: Context): String {
    return when (this) {
        is CallErrors.ErrorEmptyData -> context.getString(R.string.error_empty_data)
        is CallErrors.ErrorServer -> context.getString(R.string.error_server_error)
        is CallErrors.ErrorException -> context.getString(
            R.string.error_exception
        )
    }
}