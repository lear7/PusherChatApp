package com.gkd.demo_app_mavericks.counter

import com.airbnb.mvrx.MavericksViewModel

/** ViewModels are where all of your business logic lives. It has a simple lifecycle and is easy to test. */
class CounterViewModel(initialState: CounterState) :
    MavericksViewModel<CounterState>(initialState) {

    fun incrementCount() = setState {
        copy(count = count + 1)
    }
}