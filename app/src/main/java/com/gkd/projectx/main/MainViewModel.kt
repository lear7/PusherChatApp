package com.gkd.projectx.main

import com.gkd.data.managers.CharactersRepository
import com.gkd.projectx.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: CharactersRepository) :
    BaseViewModel<MainIntent, MainAction, MainState>() {

    override fun intentToAction(intent: MainIntent): MainAction {
        return when (intent) {
            is MainIntent.LoadAllCharacters -> MainAction.AllCharacters
            is MainIntent.ClearSearch -> MainAction.AllCharacters
            is MainIntent.SearchCharacter -> MainAction.SearchCharacters(intent.name)
        }
    }

    override fun handleAction(action: MainAction) {
        launchOnUI {
            when (action) {
                is MainAction.AllCharacters -> {
                    repository.getAllCharacters().collect {
                        mState.postValue(it.reduce())
                    }
                }
                is MainAction.SearchCharacters -> {
                    repository.searchCharacters(action.name).collect {
                        mState.postValue(it.reduce(true))
                    }
                }
            }
        }
    }
}