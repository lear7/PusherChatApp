package com.gkd.projectx.main

import android.util.Log
import com.gkd.data.managers.CharactersRepository
import com.gkd.projectx.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dataRepository: CharactersRepository) :
    BaseViewModel<MainIntent, MainAction, MainState>() {

    init {
        Log.e("Chat", "MainViewModel implementing...")
    }

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
                    dataRepository.getAllCharacters().collect {
                        mState.postValue(it.reduce())
                    }
                }
                is MainAction.SearchCharacters -> {
                    dataRepository.searchCharacters(action.name).collect {
                        mState.postValue(it.reduce(true))
                    }
                }
            }
        }
    }
}