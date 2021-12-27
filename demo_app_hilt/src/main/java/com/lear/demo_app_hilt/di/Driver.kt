package com.lear.demo_app_hilt.di

import android.content.Context
import android.util.Log
import com.lear.demo_app_hilt.App
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Driver @Inject constructor(app: App, @ApplicationContext val context: Context) {

    init {
        Log.d(App.TAG, "I am driver created by $context")
    }
}