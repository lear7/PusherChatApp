package com.lear.chatdemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        val TAG = "ChatActivity"

        lateinit var user: String

        var count: Int = 0
        val isRemote = true
    }
}