package com.gkd.demo_app_mvi.layer_app.activity

import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.gkd.demo_app_mvi.databinding.ActivityHomeBinding
import com.gkd.demo_app_mvi.layer_app.App
import com.gkd.demo_app_mvi.layer_app.activity.contract.HomeAction
import com.gkd.demo_app_mvi.layer_app.activity.contract.HomeIntent
import com.gkd.demo_app_mvi.layer_app.activity.contract.HomeState
import com.gkd.demo_app_mvi.layer_app.adapter.CharactersAdapter
import com.gkd.demo_app_mvi.layer_app.common.BaseActivity
import com.gkd.demo_app_mvi.layer_app.ext.getMessage
import com.gkd.demo_app_mvi.layer_app.ext.runIfTrue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity :
    BaseActivity<HomeIntent, HomeAction, HomeState, HomeViewModel, ActivityHomeBinding>(
        HomeViewModel::class.java,
        {
            ActivityHomeBinding.inflate(it)
        }) {

    private val mAdapter = CharactersAdapter()

    override fun initUI() {
        b.homeListCharacters.adapter = mAdapter
    }

    override fun initDATA() {
        dispatchIntent(HomeIntent.LoadAllCharacters)
    }

    override fun initEVENT() {
        b.homeSearchImage.setOnClickListener {
            b.homeSearchText.text.isNullOrBlank().not().runIfTrue {
                dispatchIntent(HomeIntent.SearchCharacter(b.homeSearchText.text.toString()))
            }
        }
        b.homeSearchText.doOnTextChanged { text, _, _, _ ->
            text.isNullOrBlank()
                .and(mState is HomeState.ResultSearch)
                .runIfTrue {
                    dispatchIntent(HomeIntent.ClearSearch)
                }
        }
    }

    override fun render(state: HomeState) {
        b.homeProgress.isVisible = state is HomeState.Loading
        b.homeMessage.isVisible = state is HomeState.Exception
        b.homeListCharacters.isVisible =
            state is HomeState.ResultSearch || state is HomeState.ResultAllPersona

        when (state) {
            is HomeState.ResultAllPersona -> {
                mAdapter.updateList(state.data)
            }
            is HomeState.ResultSearch -> {
                mAdapter.updateList(state.data)
                // other logic ...
            }
            is HomeState.Exception -> {
                state.callErrors.getMessage(this).let {
                    b.homeMessage.text = it
                    Log.e(App.TAG, state.callErrors.toString())
                }
            }
        }
    }
}