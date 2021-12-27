package com.lear.demo_app_hilt.di

import android.util.Log
import com.lear.demo_app_hilt.App
import javax.inject.Inject

interface Engine {
    fun start()
    fun shutdown()
}