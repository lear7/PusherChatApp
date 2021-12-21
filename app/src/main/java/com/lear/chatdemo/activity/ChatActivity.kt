package com.lear.chatdemo.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lear.chatdemo.App
import com.lear.chatdemo.MessageAdapter
import com.lear.chatdemo.R
import com.lear.chatdemo.databinding.ActivityChatBinding
import com.lear.chatdemo.db.ObjectBox
import com.lear.chatdemo.model.Message
import com.lear.chatdemo.network.ChatService
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
import javax.inject.Inject

class ChatActivity : AppCompatActivity() {

    private val MAX_CHANNEL = App.count
    private val MSG_PERIOD = 100L

    @Inject
    lateinit var retrofit: Retrofit

    private var userName = "${App.user}"

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: MessageAdapter

    private val messageBox = ObjectBox.store.boxFor(Message::class.java)

    private var pusher: Pusher? = null

    private val instanceId =
        if (App.isRemote) "ea5b07a6-16ff-4a44-aa2d-07aa23ce54f9" else "ea5b07a6-16ff-4a44-aa2d-07aa23ce54f9"
    private val endPoint =
        if (App.isRemote) App.baseUrl + "pusher/auth" else App.baseUrl + "auth"

    private lateinit var channelList: ArrayList<String>

    private val eventName = "new_message"
    private var socketId = ""

    private val serverAp1 =
        ServerInfo("1316846", "b60a9a22230f313794df", "03d0454ce0a082c90a12", "ap1")
    private val serverEU =
        ServerInfo("1320253", "c6b43b2f27a3660a01da", "cd670a1502e8c3fb614f", "eu")

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initChannelList()
        settingAdapter()
        readHistory()

        binding.btnSend.setOnClickListener {
            val text = binding.txtMessage.text.toString()
            if (text.isNotEmpty()) {
                doInBatch {
                    sendMessage(it, text)
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Message should not be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        setupPusher {
            doInBatch {
                goOnline(it)
            }
        }

        setupBeams()
    }

    private fun settingAdapter() {
        binding.messageList.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdapter(this)
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
            channelList.add("private-$userName-$i")
        }

    }

    private fun createMessage(channelName: String, content: String): Message {
        return Message(
            "lihua",
            userName,
            channelName = channelName,
            eventName = eventName,
            content = content,
            createTime = System.currentTimeMillis()
        )
    }

    private fun sendMessage(channelName: String, text: String) {
        val body: RequestBody =
            RequestBody.create(
                MediaType.parse("application/json"),
                Gson().toJson(createMessage(channelName, text))
            )
        val call = ChatService.create().postMessage(body)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                resetInput()
                if (!response.isSuccessful) {
                    Log.e(App.TAG, response.code().toString());
                    Toast.makeText(
                        applicationContext,
                        "Response failed:${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                resetInput()
                Log.e(App.TAG, t.toString());
                Toast.makeText(
                    applicationContext,
                    "Send failed:${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setupBeams() {
        PushNotifications.start(applicationContext, instanceId)
        PushNotifications.addDeviceInterest("hello")

        PushNotifications.setOnDeviceInterestsChangedListener(object :
            SubscriptionsChangedListener {
            override fun onSubscriptionsChanged(interests: Set<String>) {
                Log.d("PUSHER", "收到消息$interests")
            }

        })
    }

    private fun resetInput() {
        binding.txtMessage.text.clear()
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun getServerInfo(): ServerInfo {
        if (App.cluster.equals("ap1")) {
            return serverAp1
        } else {
            return serverEU
        }
    }

    private fun setupPusher(next: () -> Unit = {}) {
        val authorizer = HttpAuthorizer(endPoint)
        val options = PusherOptions()
        options.authorizer = authorizer
        options.setCluster(getServerInfo().cluster)

        pusher = Pusher(getServerInfo().key, options)
        pusher?.let {
            it.connect(object : ConnectionEventListener {
                override fun onConnectionStateChange(change: ConnectionStateChange?) {
                    Log.d(App.TAG, "onConnectionStateChange: $change")
                    change?.let {
                        runOnUiThread {
                            binding.labelStatus.text = it.currentState.toString()
                        }
                        when (change.currentState) {
                            ConnectionState.CONNECTED -> {
                                socketId = pusher?.connection?.socketId ?: ""
                                Log.d(App.TAG, "CONNECTED socketId: $socketId")
                                next()
                            }
                            else -> {
                                // ignore
                            }
                        }
                    }
                }

                override fun onError(message: String?, code: String?, e: Exception?) {
                    Log.d(App.TAG, "onError: $message, code: $code, error: ${e?.localizedMessage}")
                }
            })
        }
    }

    private fun goOnline(channelName: String) {
        pusher?.let {
            var channel: Channel? = null
            try {
                channel = it.subscribePrivate(channelName)

            } catch (e: Exception) {
                Log.e(App.TAG, "channelName subscribed.")
            }
            channel?.let { it1 ->
                it1.bind(eventName, object : PrivateChannelEventListener {
                    override fun onEvent(
                        channelName: String?,
                        eventName: String?,
                        data: String?
                    ) {
                        Log.d(App.TAG, "onMessage received: ${data}")
                        showMessage(data)
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
            doInBatch {
                it1.unsubscribe(it)
            }
        } ?: setupPusher()
    }

    private fun showMessage(data: String?) {
        val message = Gson().fromJson(data, Message::class.java)
        runOnUiThread {
            adapter.addMessage(message)
            messageBox.put(message)
            setMessageCount()
            binding.messageList.scrollToPosition(adapter.itemCount - 1);
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chat_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_subscribe -> {
                doInBatch {
                    goOnline(it)
                }
            }
            R.id.menu_unsubscribe -> {
                goOffline()
            }
            else -> {}
        }
        return true

    }

}