package com.gkd.projectx.home.home

import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.gkd.projectx.App
import com.gkd.projectx.common.BaseFragment
import com.gkd.projectx.databinding.FragmentHomeBinding
import com.gkd.projectx.ext.getMessage
import com.gkd.projectx.ext.runIfTrue
import com.gkd.projectx.home.home.contract.HomeAction
import com.gkd.projectx.home.home.contract.HomeIntent
import com.gkd.projectx.home.home.contract.HomeState
import com.gkd.projectx.main.ui.CharactersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<HomeIntent, HomeAction, HomeState, HomeViewModel, FragmentHomeBinding>(
        HomeViewModel::class.java,
        { inflater, container ->
            FragmentHomeBinding.inflate(inflater, container, false)
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
                state.callErrors.getMessage(requireContext()).let {
                    b.homeMessage.text = it
                    Log.e(App.TAG, state.callErrors.toString())
                }
            }
        }
    }

}