package com.gkd.projectx

import android.app.Application
import com.gkd.lib_chat.app.ChatManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    public var count = 0
    public var cluster = ""
    public var fromUser = ""
    public var toUser = ""

    override fun onCreate() {
        super.onCreate()
        ChatManager.initService(this)
    }
}