package com.gkd.projectx.home.home

import com.gkd.data.managers.CharactersRepository
import com.gkd.projectx.common.BaseViewModel
import com.gkd.projectx.home.home.contract.HomeAction
import com.gkd.projectx.home.home.contract.HomeIntent
import com.gkd.projectx.home.home.contract.HomeState
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * Created by Rim Gazzah on 8/26/20.
 **/
class HomeViewModel @Inject constructor(private val repository: CharactersRepository) :
    BaseViewModel<HomeIntent, HomeAction, HomeState>() {
    override fun intentToAction(intent: HomeIntent): HomeAction {
        return when (intent) {
            is HomeIntent.LoadAllCharacters -> HomeAction.AllCharacters
            is HomeIntent.ClearSearch -> HomeAction.AllCharacters
            is HomeIntent.SearchCharacter -> HomeAction.SearchCharacters(intent.name)
        }
    }

    override fun handleAction(action: HomeAction) {
        launchOnUI {
            when (action) {
                is HomeAction.AllCharacters -> {
                    repository.getAllCharacters().collect {
                        mState.postValue(it.reduce())
                    }
                }
                is HomeAction.SearchCharacters -> {
                    repository.searchCharacters(action.name).collect {
                        mState.postValue(it.reduce(true))
                    }
                }
            }
        }
    }
}