package com.gkd.demo_app_mvi.layer_app.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * Created by Rim Gazzah on 8/19/20.
 **/
abstract class BaseFragment<
        INTENT : ViewIntent,
        ACTION : ViewAction,
        STATE : ViewState,
        VM : BaseViewModel<INTENT, ACTION, STATE>,
        Binding : ViewBinding?>(
    modelClass: Class<VM>,
    open val bindingFactory: (LayoutInflater, ViewGroup) -> Binding
) : Fragment(), IViewRenderer<STATE> {

    private var _b: Binding? = null
    val b get() = _b!!

    private lateinit var viewState: STATE
    val mState get() = viewState

    val viewModel: VM by lazy {
        ViewModelProvider(requireActivity()).get(modelClass.kotlin.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _b = bindingFactory(layoutInflater, container!!)
        return _b!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        viewModel.state.observe(viewLifecycleOwner) {
            viewState = it
            render(it)
        }
        initDATA()
        initEVENT()
    }

    override fun onDestroyView() {
        _b = null
        super.onDestroyView()
    }

    abstract fun initUI()
    abstract fun initDATA()
    abstract fun initEVENT()

    fun dispatchIntent(intent: INTENT) {
        viewModel.dispatchIntent(intent)
    }
}