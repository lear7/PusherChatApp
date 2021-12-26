package com.gkd.projectx.home.home

import com.gkd.data.common.DataResult
import com.gkd.domain.entities.Persona
import com.gkd.projectx.home.home.contract.HomeState

/**
 * Created by Rim Gazzah on 8/31/20.
 **/

fun DataResult<List<Persona>>.reduce(isSearchMode: Boolean = false): HomeState {
    return when (this) {
        is DataResult.Success -> if (isSearchMode) HomeState.ResultSearch(data) else HomeState.ResultAllPersona(
            data
        )
        is DataResult.Error -> HomeState.Exception(exception)
        is DataResult.Loading -> HomeState.Loading
    }
}