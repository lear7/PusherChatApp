package com.lear.chatdemo

import android.app.Application
import com.lear.chatdemo.activity.ServerInfo
import com.lear.chatdemo.db.ObjectBox
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        val TAG = "ChatActivity"

        lateinit var user: String

        private val serverAp1 =
            ServerInfo("1316846", "b60a9a22230f313794df", "03d0454ce0a082c90a12", "ap1")
        private val serverEU =
            ServerInfo("1320253", "c6b43b2f27a3660a01da", "cd670a1502e8c3fb614f", "eu")
        private val serverUS2 =
            ServerInfo("1320329", "59fdb4d892b07745de4c", "59fdb4d892b07745de4c", "us2")


        var count: Int = 0
        var cluster = "ap1"
        val isRemote = false
        val inBatch = false

        val baseUrl = if (isRemote) "http://192.168.10.54:9950/" else "http://192.168.6.217:8080/"

        fun getServerInfo(): ServerInfo {
            if (cluster.startsWith("us2")) {
                return serverUS2
            } else if (cluster.startsWith("eu")) {
                return serverEU
            } else {
                return serverAp1
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}