package com.gkd.projectx.home.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gkd.lib_chat.app.ChatManager
import com.gkd.lib_chat.app.ChatViewFragment
import com.gkd.projectx.App
import com.gkd.projectx.R
import com.gkd.projectx.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var app: App

    lateinit var chatViewFragment: ChatViewFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initViews()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        chatViewFragment = ChatViewFragment()
        ChatManager.initChat(app.cluster, app.fromUser, app.toUser)
        childFragmentManager.beginTransaction().replace(R.id.chat_container, chatViewFragment)
            .commitAllowingStateLoss()
    }
}