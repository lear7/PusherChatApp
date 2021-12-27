package com.gkd.demo_app_mvi.layer_app.activity

import com.gkd.demo_app_mvi.layer_app.activity.contract.HomeState
import com.gkd.demo_app_mvi.layer_data.common.DataResult
import com.gkd.domain.entities.Persona

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