package com.gkd.demo_app_mvi.layer_app.common

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * Created by Rim Gazzah on 8/19/20.
 **/
abstract class BaseActivity<
        INTENT : ViewIntent,
        ACTION : ViewAction,
        STATE : ViewState,
        VM : BaseViewModel<INTENT, ACTION, STATE>,
        Binding : ViewBinding?>(
    modelClass: Class<VM>,
    open val bindingFactory: (LayoutInflater) -> Binding
) : AppCompatActivity(), IViewRenderer<STATE> {

    private var _b: Binding? = null
    val b get() = _b!!

    private lateinit var viewState: STATE
    val mState get() = viewState

    val viewModel: VM by lazy {
        ViewModelProvider(this).get(modelClass.kotlin.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _b = bindingFactory(layoutInflater)
        setContentView(_b!!.root)

        initUI()
        viewModel.state.observe(this) {
            viewState = it
            render(it)
        }
        initDATA()
        initEVENT()
    }

    override fun onDestroy() {
        super.onDestroy()
        _b = null
    }

    abstract fun initUI()
    abstract fun initDATA()
    abstract fun initEVENT()

    fun dispatchIntent(intent: INTENT) {
        viewModel.dispatchIntent(intent)
    }
}