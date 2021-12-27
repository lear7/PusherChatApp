package com.gkd.demo_app_mavericks

import android.app.Application
import com.airbnb.mvrx.Mavericks

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Mavericks.initialize(this)
    }
}