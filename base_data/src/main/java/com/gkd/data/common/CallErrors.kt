package com.gkd.data.common


/**
 * Created by Rim Gazzah on 8/28/20.
 **/
sealed class CallErrors {
    object ErrorEmptyData : CallErrors()
    object ErrorServer: CallErrors()
    data class ErrorException(val throwable: Throwable) : CallErrors()
}