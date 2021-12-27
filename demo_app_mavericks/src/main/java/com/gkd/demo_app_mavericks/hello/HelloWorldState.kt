package com.gkd.demo_app_mavericks.hello

import com.airbnb.mvrx.MavericksState

data class HelloWorldState(val title: String = "Hello World") : MavericksState