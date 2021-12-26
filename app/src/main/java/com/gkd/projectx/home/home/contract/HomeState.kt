package com.gkd.projectx.home.home.contract

import com.gkd.data.common.CallErrors
import com.gkd.domain.entities.Persona
import com.gkd.projectx.common.ViewState

/**
 * Created by Rim Gazzah on 8/26/20.
 **/
sealed class HomeState : ViewState {
    object Loading : HomeState()
    data class ResultAllPersona(val data: List<Persona>) : HomeState()
    data class ResultSearch(val data: List<Persona>) : HomeState()
    data class Exception(val callErrors: CallErrors) : HomeState()
}