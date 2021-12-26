package com.gkd.projectx.home.home.contract

import com.gkd.projectx.common.ViewAction

/**
 * Created by Rim Gazzah on 8/26/20.
 **/
sealed class HomeAction : ViewAction {
    data class SearchCharacters(val name: String) : HomeAction()
    object AllCharacters : HomeAction()
}