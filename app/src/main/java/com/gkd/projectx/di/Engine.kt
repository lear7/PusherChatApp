package com.gkd.projectx.di

import android.util.Log
import com.gkd.projectx.App
import javax.inject.Inject
import javax.inject.Qualifier

interface Engine {
    fun start()
    fun shutdown()
}

class GasEngine @Inject constructor() : Engine {
    override fun start() {
        Log.d(App.TAG, "Gas engine start.")
    }

    override fun shutdown() {
        Log.d(App.TAG, "Gas engine shutdown.")
    }

}

class ElectricEngine @Inject constructor() : Engine {
    override fun start() {
        Log.d(App.TAG, "Electric engine start.")
    }

    override fun shutdown() {
        Log.d(App.TAG, "Electric engine shutdown.")
    }
}