package com.gkd.demo_app_mvi.layer_app.fragment

import com.gkd.demo_app_mvi.layer_app.common.BaseViewModel
import com.gkd.demo_app_mvi.layer_app.fragment.contract.HomeAction
import com.gkd.demo_app_mvi.layer_app.fragment.contract.HomeIntent
import com.gkd.demo_app_mvi.layer_app.fragment.contract.HomeState
import com.gkd.demo_app_mvi.layer_data.managers.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * Created by Rim Gazzah on 8/26/20.
 **/
@HiltViewModel
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