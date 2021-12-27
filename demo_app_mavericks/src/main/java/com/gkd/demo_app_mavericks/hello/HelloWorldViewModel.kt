package com.gkd.demo_app_mavericks.hello

import com.airbnb.mvrx.MavericksViewModel

class HelloWorldViewModel(initialState: HelloWorldState) :
    MavericksViewModel<HelloWorldState>(initialState) {

    fun getMoreExcited() = setState {
        copy(title = "$title!")
    }
}
