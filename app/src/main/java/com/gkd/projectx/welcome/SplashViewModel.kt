package com.gkd.projectx.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SplashViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Welocome"
    }
    val text: LiveData<String> = _text
}