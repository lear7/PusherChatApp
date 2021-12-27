package com.gkd.lib_chat.app

import android.util.Log
import com.gkd.lib_chat.data.model.Message
import com.google.gson.Gson

fun String.asMessage(): Message? {
    try {
        return Gson().fromJson(this, Message::class.java)
    } catch (e: Exception) {
        Log.e(ChatManager.TAG, "parsing message failed: ${e.localizedMessage}")
    }
    return null
}