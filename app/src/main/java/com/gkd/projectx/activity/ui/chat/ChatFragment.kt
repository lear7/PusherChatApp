package com.gkd.projectx.activity.ui.chat

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkd.projectx.App
import com.gkd.projectx.R
import com.gkd.projectx.activity.ui.chat.model.Message
import com.gkd.projectx.activity.ui.chat.model.Sent
import com.gkd.projectx.adapter.MessageAdapter
import com.gkd.projectx.databinding.FragmentChatBinding
import com.gkd.projectx.db.ObjectBox
import com.gkd.projectx.ext.asMessage
import com.gkd.projectx.network.ChatService
import com.google.gson.Gson
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.Channel
import com.pusher.client.channel.PrivateChannelEventListener
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import com.pusher.client.util.HttpAuthorizer
import com.pusher.pushnotifications.PushNotifications
import com.pusher.pushnotifications.SubscriptionsChangedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var vm: ChatViewModel? = null

    private val MAX_CHANNEL = App.count

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var chatService: ChatService

    private lateinit var adapter: MessageAdapter

    private val messageBox = ObjectBox.store.boxFor(Message::class.java)

    private var pusher: Pusher? = null

    private val instanceId =
        if (App.isRemote) "ea5b07a6-16ff-4a44-aa2d-07aa23ce54f9" else "ea5b07a6-16ff-4a44-aa2d-07aa23ce54f9"
    private val endPoint =
        if (App.isRemote) App.baseUrl + "pusher/auth" else App.baseUrl + "auth"

    private lateinit var channelList: ArrayList<String>

    private val eventName = "new_message"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(this).get(ChatViewModel::class.java)
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
        // initChannelList()
        settingAdapter()
        readHistory()

        binding.labelCluster.text = App.cluster.uppercase(Locale.getDefault())
        binding.labelTarget.text = "Chat with ${App.toUser}"

        binding.btnSend.setOnClickListener {
            val text = binding.txtMessage.text
            text.isNullOrBlank().not().run {
                val pendingMessage =
                    createMessage(
                        App.cluster,
                        App.getTargetChannel(),
                        eventName,
                        text.toString(),
                        vm?.getMsgId() ?: ""
                    )
                sendMessage(pendingMessage)
                addLocalMessage(pendingMessage)
            }
        }

        setupPusher { goOnline() }

        setupBeams()
    }

    fun doInBatch(time: Long = 10, process: (channel: String) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            channelList.forEach {
                process(it)
                delay(time)
            }
        }
    }

    private fun readHistory() {
        adapter.setMessage(ArrayList(messageBox.all))
        setMessageCount()
    }

    private fun settingAdapter() {
        binding.messageList.layoutManager = LinearLayoutManager(requireContext())
        adapter = MessageAdapter(requireContext())
        binding.messageList.adapter = adapter
        binding.labelCount.setOnClickListener {
            adapter.clearMessage()
            messageBox.removeAll()
            setMessageCount()
        }
        setMessageCount()
    }

    private fun setMessageCount() {
        binding.labelCount.text = getString(R.string.message_count, adapter.itemCount)

    }

    private fun initChannelList() {
        channelList = ArrayList()
        for (i in 0 until if (App.inBatch) MAX_CHANNEL else 1) {
            if (i == 0) {
                channelList.add("private-${App.toUser}")
            } else {
                channelList.add("private-${App.toUser}-$i")
            }
        }
    }

    private fun createMessage(
        cluster: String? = "",
        channelName: String,
        eventName: String,
        content: String? = "",
        msgId: String
    ): Message {
        return Message(
            msgId = msgId,
            from = App.fromUser,
            to = App.toUser,
            channelName = channelName,
            eventName = eventName,
            cluster = cluster,
            content = content,
            createTime = System.currentTimeMillis()
        )
    }

    private fun notifyServer(channelName: String, eventName: String, msgId: String) {
        val body: RequestBody =
            RequestBody.create(
                MediaType.parse("application/json"),
                Gson().toJson(
                    listOf(
                        createMessage(
                            channelName = channelName,
                            eventName = eventName,
                            msgId = msgId
                        )
                    )
                )
            )
        val call = chatService.delivered(body)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onMessageDelivered(response)
                } else {
                    onMessageDeliverFailed(Throwable("response failed."))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onMessageDeliverFailed(t)
            }
        })
    }

    private fun sendMessage(message: Message) {
        val body: RequestBody =
            RequestBody.create(MediaType.parse("application/json"), Gson().toJson(message))
        val call = chatService.postMessage(body)

        call.enqueue(object : Callback<Sent> {
            override fun onResponse(call: Call<Sent>, response: Response<Sent>) {
                if (response.isSuccessful) {
                    resetInput()
                    onMessageSent(response)
                } else {
                    onMessageSendFailed(Throwable("response failed."))
                }
            }

            override fun onFailure(call: Call<Sent>, t: Throwable) {
                onMessageSendFailed(t)
            }
        })
    }

    private fun onMessageDelivered(response: Response<Void>) {
        Log.d(App.TAG, "onMessageDelivered: $response")
    }

    private fun onMessageDeliverFailed(t: Throwable?) {
        Log.d(App.TAG, "onMessageDeliverFailed: ${t?.localizedMessage}")
    }

    private fun onMessageSent(response: Response<Sent>) {
        try {
            response.body()?.let {
                lifecycleScope.launchWhenStarted {
                    adapter.updateMessageState(it.msgId, 0)?.let {
                        messageBox.put(it)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(App.TAG, "onMessageSent parse failed:${e.localizedMessage}")
        }

    }

    private fun onMessageSendFailed(t: Throwable?) {
        resetInput()
        Log.d(App.TAG, "onMessageSendFailed: ${t?.localizedMessage}")
        Toast.makeText(
            requireContext(),
            "Send failed:${t?.localizedMessage}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun addLocalMessage(message: Message) {
        onMessageReceived(message)
    }

    private fun onMessageReceived(message: Message) {
        lifecycleScope.launchWhenResumed {
            when (message.msgType) {
                0 -> {
                    adapter.addMessage(message)
                    messageBox.put(message)
                    setMessageCount()
                    binding.messageList.scrollToPosition(adapter.itemCount - 1)
                    // if message if send to me, send a notification to server
                    if (TextUtils.equals(App.fromUser, message.to)) {
                        notifyServer(
                            message.channelName ?: "",
                            message.eventName ?: "",
                            message.msgId ?: ""
                        )
                    }
                }
                6 -> {
                    message.msgId?.let {
                        adapter.updateMessageState(it, message.msgState)?.let {
                            messageBox.put(it)
                        }
                    }
                }
                else -> {
                    // ignore
                }
            }
        }
    }

    private fun setupBeams() {
//        PushNotifications.start(requireContext(), instanceId)
//        PushNotifications.addDeviceInterest("hello")
//
//        PushNotifications.setOnDeviceInterestsChangedListener(object :
//            SubscriptionsChangedListener {
//            override fun onSubscriptionsChanged(interests: Set<String>) {
//                Log.d("PUSHER", "收到消息$interests")
//            }
//
//        })
    }

    private fun resetInput() {
        binding.txtMessage.text.clear()
        val inputManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            requireActivity().currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun setupPusher(next: () -> Unit = {}) {
        val authorizer = HttpAuthorizer(endPoint)
        var headers = HashMap<String, String>()
        headers["clusterName"] = App.getServerInfo().cluster
        headers["userId"] = App.fromUser
        authorizer.setHeaders(headers)

        val options = PusherOptions()
        options.authorizer = authorizer
        options.setCluster(App.getServerInfo().cluster)

        pusher = Pusher(App.getServerInfo().key, options)
        pusher?.let {
            it.connect(object : ConnectionEventListener {
                override fun onConnectionStateChange(change: ConnectionStateChange?) {
                    Log.d(App.TAG, "onConnectionStateChange: $change")
                    change?.let {
                        lifecycleScope.launchWhenStarted {
                            binding.labelStatus.text = it.currentState.toString()
                        }
                        when (change.currentState) {
                            ConnectionState.CONNECTED -> {
                                val socketId = pusher?.connection?.socketId ?: ""
                                Log.v(App.TAG, "Connected socketId: $socketId")
                                next()
                            }
                            else -> {
                                // ignore
                            }
                        }
                    }
                }

                override fun onError(message: String?, code: String?, e: Exception?) {
                    Log.e(App.TAG, "onError: $message, code: $code, error: ${e?.localizedMessage}")
                }
            })
        }
    }

    private fun goOnline() {
        pusher?.let {
            var channel: Channel? = null
            try {
                channel = it.subscribePrivate(App.getMyChannel())
            } catch (e: Exception) {
                Log.e(
                    App.TAG,
                    "Channel: ${App.getMyChannel()} subscribe failed: ${e.localizedMessage}"
                )
            }
            channel?.let { it1 ->
                it1.bind(eventName, object : PrivateChannelEventListener {
                    override fun onEvent(
                        channelName: String?,
                        eventName: String?,
                        data: String?
                    ) {
                        Log.d(App.TAG, "onMessage received: ${data}")
                        data?.asMessage()?.let {
                            onMessageReceived(it)
                        }
                    }

                    override fun onSubscriptionSucceeded(channelName: String?) {
                        Log.d(App.TAG, "onSubscriptionSucceeded: ${channelName}")
                    }

                    override fun onAuthenticationFailure(
                        message: String?,
                        e: java.lang.Exception?
                    ) {
                        Log.d(App.TAG, "onAuthenticationFailure: ${e?.localizedMessage}")
                    }
                })

                Log.d(App.TAG, "channel $channel bound.")
            }
        } ?: setupPusher()
    }

    private fun goOffline() {
        pusher?.let { it1 ->
            it1.unsubscribe(App.getMyChannel())
        } ?: setupPusher()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_subscribe -> {
                goOnline()
            }
            R.id.menu_unsubscribe -> {
                goOffline()
            }
            else -> {}
        }
        return true

    }
}