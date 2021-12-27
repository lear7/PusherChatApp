package com.gkd.demo_app_mavericks.counter

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.gkd.demo_app_mavericks.R

/**
 * Fragments in Mavericks are simple and rarely do more than bind your state to views.
 * Mavericks works well with Fragments but you can use it with whatever view architecture you use.
 */
class CounterFragment : Fragment(R.layout.fragment_counter), MavericksView {

    private val viewModel: CounterViewModel by fragmentViewModel()
    private lateinit var counterText: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        counterText = view.findViewById<TextView>(R.id.counter_text)

        counterText.setOnClickListener {
            viewModel.incrementCount()
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        counterText.text = "Count: ${state.count}"
    }
}