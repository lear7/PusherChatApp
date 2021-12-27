package com.gkd.projectx.welcome.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gkd.projectx.App
import com.gkd.projectx.R
import com.gkd.projectx.databinding.FragmentLoginBinding
import com.gkd.projectx.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks
import reactivecircus.flowbinding.android.widget.checkedChanges
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var app: App

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        binding.btnLogin.clicks().onEach {
            app.count = binding.channelCount.text.toString().toInt()
            startActivity(
                Intent(
                    requireActivity(), MainActivity::class.java
                )
            )
            requireActivity().finish()
        }.launchIn(lifecycleScope)

        binding.radioUserName.checkedChanges().onEach {
            when (it) {
                R.id.user_name1 -> {
                    app.fromUser = binding.userName1.text.toString()
                    app.toUser = binding.userName2.text.toString()
                }
                R.id.user_name2 -> {
                    app.fromUser = binding.userName2.text.toString()
                    app.toUser = binding.userName1.text.toString()
                }
                else -> ""
            }
        }.launchIn(lifecycleScope)

        binding.radioArea.checkedChanges().skipInitialValue().onEach {
            when (it) {
                R.id.radio_area_1 -> {
                    app.cluster = "ap1"
                }
                R.id.radio_area_2 -> {
                    app.cluster = "us2"
                }
                R.id.radio_area_3 -> {
                    app.cluster = "eu"
                }
            }
            Toast.makeText(
                app,
                "切换成功",
                Toast.LENGTH_SHORT
            ).show()
        }.launchIn(lifecycleScope)
    }

}