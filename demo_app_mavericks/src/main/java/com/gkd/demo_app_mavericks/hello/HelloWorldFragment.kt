package com.gkd.demo_app_mavericks.hello

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.gkd.demo_app_mavericks.R
import com.gkd.demo_app_mavericks.databinding.FragmentHelloWorldBinding

class HelloWorldFragment : Fragment(R.layout.fragment_hello_world), MavericksView {

    private var _binding: FragmentHelloWorldBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HelloWorldViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelloWorldBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun invalidate() = withState(viewModel) { state ->
        binding.textValue.text = state.title
    }

}