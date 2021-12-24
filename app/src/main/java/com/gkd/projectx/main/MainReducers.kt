package com.gkd.projectx.main

import com.gkd.data.common.DataResult
import com.gkd.domain.entities.Persona

/**
 * Created by Rim Gazzah on 8/31/20.
 **/

fun DataResult<List<Persona>>.reduce(isSearchMode: Boolean = false): MainState {
    return when (this) {
        is DataResult.Success -> if (isSearchMode) MainState.ResultSearch(data) else MainState.ResultAllPersona(
            data
        )
        is DataResult.Error -> MainState.Exception(exception)
        is DataResult.Loading -> MainState.Loading
    }
}