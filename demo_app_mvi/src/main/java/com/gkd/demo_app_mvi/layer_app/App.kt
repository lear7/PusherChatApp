package com.gkd.demo_app_mvi.layer_app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        val TAG = "MVI_DEMO"
    }

}