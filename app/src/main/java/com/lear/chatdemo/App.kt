package com.lear.chatdemo

import android.app.Application

class App : Application() {
    companion object {
        lateinit var user: String
        val isRemote = false
    }
}