package com.gkd.projectx.main

import com.gkd.projectx.common.ViewIntent

/**
 * Created by Rim Gazzah on 8/26/20.
 **/
sealed class MainIntent : ViewIntent {
    object LoadAllCharacters : MainIntent()
    data class SearchCharacter(val name: String) : MainIntent()
    object ClearSearch : MainIntent()
}