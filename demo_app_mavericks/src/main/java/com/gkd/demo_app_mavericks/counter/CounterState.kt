package com.gkd.demo_app_mavericks.counter

import com.airbnb.mvrx.MavericksState

/** State classes contain all of the data you need to render a screen. */
data class CounterState(val count: Int = 0) : MavericksState