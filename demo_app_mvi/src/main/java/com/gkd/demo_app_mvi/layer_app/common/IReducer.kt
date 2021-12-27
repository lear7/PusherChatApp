package com.gkd.demo_app_mvi.layer_app.common

import com.gkd.demo_app_mvi.layer_data.common.DataResult

/**
 * Created by Rim Gazzah on 8/26/20.
 **/
interface IReducer<STATE, T : Any> {
    fun reduce(dataResult: DataResult<T>, state: STATE): STATE
}