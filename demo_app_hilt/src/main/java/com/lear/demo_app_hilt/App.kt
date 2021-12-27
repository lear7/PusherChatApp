package com.lear.demo_app_hilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        val TAG = "Hilt Demo"
    }

}