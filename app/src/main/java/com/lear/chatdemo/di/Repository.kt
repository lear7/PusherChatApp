package com.lear.chatdemo.di

import android.util.Log
import com.lear.chatdemo.App
import javax.inject.Inject

class Repository @Inject constructor() {

//    init {
//        Log.d(App.TAG, "Repository created $this")
//    }

    fun doRepositoryWork() {
        Log.d(App.TAG, "Do some work in Repository.")
    }

}
