package com.lear.demo_app_hilt.di

import javax.inject.Inject

class Repository @Inject constructor() {

    fun getCargo(): String {
        return "Cargo from $this"
    }

}
