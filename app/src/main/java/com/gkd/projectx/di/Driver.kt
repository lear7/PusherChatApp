package com.gkd.projectx.di

import android.content.Context
import android.util.Log
import com.gkd.projectx.App
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Driver @Inject constructor(app: App, @ApplicationContext val context: Context) {

    init {
        Log.d(App.TAG, "I am driver created by $app - $context")
    }
}