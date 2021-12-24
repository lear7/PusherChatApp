package com.gkd.projectx.main

import com.gkd.data.common.CallErrors
import com.gkd.domain.entities.Persona
import com.gkd.projectx.common.ViewState

/**
 * Created by Rim Gazzah on 8/26/20.
 **/
sealed class MainState : ViewState {
    object Loading : MainState()
    data class ResultAllPersona(val data: List<Persona>) : MainState()
    data class ResultSearch(val data: List<Persona>) : MainState()
    data class Exception(val callErrors: CallErrors) : MainState()
}