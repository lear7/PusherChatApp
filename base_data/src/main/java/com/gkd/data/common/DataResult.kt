package com.gkd.data.common

/**
 * Created by Rim Gazzah on 8/28/20.
 **/
sealed class DataResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : DataResult<T>()
    data class Error(val exception: CallErrors) : DataResult<Nothing>()
    object Loading : DataResult<Nothing>()
}