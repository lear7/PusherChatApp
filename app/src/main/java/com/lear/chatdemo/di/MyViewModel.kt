package com.lear.chatdemo.di

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lear.chatdemo.App
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    init {
        Log.d(App.TAG, "MyViewModel created $this")
    }

    fun doWork() {
        repository.doRepositoryWork()
    }

}