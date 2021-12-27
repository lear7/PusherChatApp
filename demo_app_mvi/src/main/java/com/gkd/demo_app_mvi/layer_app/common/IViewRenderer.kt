package com.gkd.demo_app_mvi.layer_app.common

/**
 * Created by Rim Gazzah on 8/20/20.
 **/
interface IViewRenderer<STATE> {
    fun render(state: STATE)
}