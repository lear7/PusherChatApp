package com.gkd.projectx.di

import android.util.Log
import com.gkd.projectx.App
import javax.inject.Inject

class Repository @Inject constructor() {

    fun doRepositoryWork() {
        Log.d(App.TAG, "Do some work in Repository.")
    }

}
