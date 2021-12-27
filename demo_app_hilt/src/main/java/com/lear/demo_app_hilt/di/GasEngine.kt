package com.lear.demo_app_hilt.di

import android.util.Log
import com.lear.demo_app_hilt.App
import javax.inject.Inject

class GasEngine @Inject constructor() : Engine {
    override fun start() {
        Log.d(App.TAG, "Gas engine start.")
    }

    override fun shutdown() {
        Log.d(App.TAG, "Gas engine shutdown.")
    }
}