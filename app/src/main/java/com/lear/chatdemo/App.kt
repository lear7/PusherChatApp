package com.lear.chatdemo

import android.app.Application
import com.lear.chatdemo.db.ObjectBox
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        val TAG = "ChatActivity"

        lateinit var user: String

        var count: Int = 0
        var cluster = ""
        val isRemote = true
        val inBatch = false
    }

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}