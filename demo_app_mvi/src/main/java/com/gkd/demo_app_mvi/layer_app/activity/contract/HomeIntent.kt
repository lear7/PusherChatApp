package com.gkd.demo_app_mvi.layer_app.activity.contract

import com.gkd.demo_app_mvi.layer_app.common.ViewIntent

/**
 * Created by Rim Gazzah on 8/26/20.
 **/
sealed class HomeIntent : ViewIntent {
    object LoadAllCharacters : HomeIntent()
    data class SearchCharacter(val name: String) : HomeIntent()
    object ClearSearch : HomeIntent()
}