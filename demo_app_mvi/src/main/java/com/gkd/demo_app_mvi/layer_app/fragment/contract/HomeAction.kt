package com.gkd.demo_app_mvi.layer_app.fragment.contract

import com.gkd.demo_app_mvi.layer_app.common.ViewAction

/**
 * Created by Rim Gazzah on 8/26/20.
 **/
sealed class HomeAction : ViewAction {
    data class SearchCharacters(val name: String) : HomeAction()
    object AllCharacters : HomeAction()
}