package com.gkd.projectx.main

import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.gkd.data.services.CharacterService
import com.gkd.projectx.App
import com.gkd.projectx.common.BaseActivity
import com.gkd.projectx.databinding.ActivityMainBinding
import com.gkd.projectx.ext.getMessage
import com.gkd.projectx.ext.runIfTrue
import com.gkd.projectx.main.ui.CharactersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

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
                state.callErrors.getMessage(this).let {
                    b.homeMessage.text = it
                    Log.e(App.TAG,state.callErrors.toString())
                }
            }
        }
    }

    @Inject
    lateinit var apiService: CharacterService

    @Inject
    lateinit var retrofit: Retrofit

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.IO) {
            Log.e(App.TAG,"hello")
            var response = retrofit.create(CharacterService::class.java).getAllCharacters()
            if (response.isSuccessful) {
                Log.e(App.TAG, "response success")
            } else {
                Log.e(App.TAG, "response not success")
            }
        }
    }
}