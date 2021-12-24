package com.gkd.projectx.di

import android.util.Log
import com.gkd.projectx.App
import com.gkd.projectx.di.module.BindElectricEngine
import com.gkd.projectx.di.module.BindGasEngine
import javax.inject.Inject

class Truck @Inject constructor(val driver: Driver) {

    @BindGasEngine
    @Inject
    lateinit var gasEngine: Engine

    @BindElectricEngine
    @Inject
    lateinit var electricEngine: Engine

    fun deliver() {
        gasEngine.start()
        electricEngine.start()
        Log.d(App.TAG, "Truck is delivering cargo. Driven by $driver")
        gasEngine.shutdown()
        electricEngine.shutdown()
    }

}