package com.gkd.projectx.common

import com.gkd.data.common.DataResult

/**
 * Created by Rim Gazzah on 8/26/20.
 **/
interface IReducer<STATE, T : Any> {
    fun reduce(dataResult: DataResult<T>, state: STATE): STATE
}