package com.lear.demo_app_hilt.di

import android.util.Log
import com.lear.demo_app_hilt.App
import com.lear.demo_app_hilt.di.module.BindElectricEngine
import com.lear.demo_app_hilt.di.module.BindGasEngine
import javax.inject.Inject

class Truck @Inject constructor(val driver: Driver) {

    @BindGasEngine
    @Inject
    lateinit var gasEngine: Engine

    @BindElectricEngine
    @Inject
    lateinit var electricEngine: Engine

    fun deliver(cargoName: String) {
        gasEngine.start()
        electricEngine.start()
        Log.e(App.TAG, "Truck $this is delivering cargo \"$cargoName\" Driven by $driver")
        gasEngine.shutdown()
        electricEngine.shutdown()
    }

}