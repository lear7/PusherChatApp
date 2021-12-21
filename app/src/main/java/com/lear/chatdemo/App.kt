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

        val baseUrl = if(isRemote) "http://192.168.10.54:9950/" else "http://192.168.6.217:8080/"
    }

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}