package com.gkd.projectx.main

import com.gkd.projectx.common.ViewAction

/**
 * Created by Rim Gazzah on 8/26/20.
 **/
sealed class MainAction : ViewAction {
    data class SearchCharacters(val name: String) : MainAction()
    object AllCharacters : MainAction()
}