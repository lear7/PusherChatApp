package com.gkd.demo_app_mvi.layer_app.fragment.contract

import com.gkd.demo_app_mvi.layer_app.common.ViewState
import com.gkd.demo_app_mvi.layer_data.common.CallErrors
import com.gkd.domain.entities.Persona

/**
 * Created by Rim Gazzah on 8/26/20.
 **/
sealed class HomeState : ViewState {
    object Loading : HomeState()
    data class ResultAllPersona(val data: List<Persona>) : HomeState()
    data class ResultSearch(val data: List<Persona>) : HomeState()
    data class Exception(val callErrors: CallErrors) : HomeState()
}