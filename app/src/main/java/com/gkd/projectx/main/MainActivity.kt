package com.gkd.projectx.main

import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.gkd.projectx.common.BaseActivity
import com.gkd.projectx.databinding.ActivityMainBinding
import com.gkd.projectx.ext.getMessage
import com.gkd.projectx.ext.runIfTrue
import com.gkd.projectx.main.ui.CharactersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    BaseActivity<MainIntent, MainAction, MainState, MainViewModel, ActivityMainBinding>(
        MainViewModel::class.java,
        {
            ActivityMainBinding.inflate(it)
        }) {

    private val mAdapter = CharactersAdapter()

    override fun initUI() {
        b.homeListCharacters.adapter = mAdapter
    }

    override fun initDATA() {
        dispatchIntent(MainIntent.LoadAllCharacters)
    }

    override fun initEVENT() {
        b.homeSearchImage.setOnClickListener {
            b.homeSearchText.text.isNullOrBlank().not().runIfTrue {
                dispatchIntent(MainIntent.SearchCharacter(b.homeSearchText.text.toString()))
            }
        }
        b.homeSearchText.doOnTextChanged { text, _, _, _ ->
            text.isNullOrBlank()
                .and(mState is MainState.ResultSearch)
                .runIfTrue {
                    dispatchIntent(MainIntent.ClearSearch)
                }
        }
    }

    override fun render(state: MainState) {
        b.homeProgress.isVisible = state is MainState.Loading
        b.homeMessage.isVisible = state is MainState.Exception
        b.homeListCharacters.isVisible =
            state is MainState.ResultSearch || state is MainState.ResultAllPersona

        when (state) {
            is MainState.ResultAllPersona -> {
                mAdapter.updateList(state.data)
            }
            is MainState.ResultSearch -> {
                mAdapter.updateList(state.data)
                // other logic ...
            }
            is MainState.Exception -> {
                b.homeMessage.text = state.callErrors.getMessage(this)
            }
        }
    }
}