package com.lear.chatdemo

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
import com.lear.chatdemo.databinding.ActivityChatBinding
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
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "ChatActivity"

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding

    private lateinit var adapter: MessageAdapter

    private var pusher: Pusher? = null

    private val instanceId =
        if (App.isRemote) "ea5b07a6-16ff-4a44-aa2d-07aa23ce54f9" else "ea5b07a6-16ff-4a44-aa2d-07aa23ce54f9"
    private val pusherAppKey = if (App.isRemote) "1072c1cf4f5d3dda5a51" else "b60a9a22230f313794df"
    private val pusherAppCluster = if (App.isRemote) "sa1" else "ap1"
    private val endPoint =
        if (App.isRemote) "http://192.168.10.40:8082/pusher/auth" else "http://192.168.6.217:8080/auth"

    private var userName = "${App.user}"
    private var channelName = "private-$userName"
    private val eventName = "new_message"

    private var channel: Channel? = null
    private var socketId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.messageList.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdapter(this)
        binding.messageList.adapter = adapter

        binding.btnSend.setOnClickListener {
            if (binding.txtMessage.text.isNotEmpty()) {
                val message = Message(
                    channelName,
                    userName,
                    binding.txtMessage.text.toString(),
                    System.currentTimeMillis()
                )

                val body: RequestBody =
                    RequestBody.create(MediaType.parse("application/json"), Gson().toJson(message))
                val call = ChatService.create().postMessage(body)

                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        resetInput()
                        if (!response.isSuccessful) {
                            Log.e(TAG, response.code().toString());
                            Toast.makeText(
                                applicationContext,
                                "Response failed:${response.message()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        resetInput()
                        Log.e(TAG, t.toString());
                        Toast.makeText(
                            applicationContext,
                            "Send failed:${t.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            } else {
                Toast.makeText(
                    applicationContext,
                    "Message should not be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        setupPusher {
            goOnline()
        }

        setupBeams()
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
        // Clean text box
        binding.txtMessage.text.clear()

        // Hide keyboard
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun setupPusher(next: () -> Unit = {}) {
        val authorizer = HttpAuthorizer(endPoint)
        val options = PusherOptions()
        options.authorizer = authorizer
        options.setCluster(pusherAppCluster)

        pusher = Pusher(pusherAppKey, options)
        pusher?.let {
            it.connect(object : ConnectionEventListener {
                override fun onConnectionStateChange(change: ConnectionStateChange?) {
                    Log.d(TAG, "onConnectionStateChange: $change")
                    change?.let {
                        when (change.currentState) {
                            ConnectionState.CONNECTED -> {
                                socketId = pusher?.connection?.socketId ?: ""
                                Log.d(TAG, "CONNECTED socketId: $socketId")
                                next()
                            }
                            else -> {
                                // ignore
                            }
                        }
                    }
                }

                override fun onError(message: String?, code: String?, e: Exception?) {
                    Log.d(TAG, "onError: $message, code: $code, error: ${e?.localizedMessage}")
                }
            })
        }
    }

    private fun goOnline() {
        pusher?.let {
            if (channel?.isSubscribed != true) {
                channel = it.subscribePrivate(channelName)
                channel?.let { it1 ->
                    it1.bind(eventName, object : PrivateChannelEventListener {
                        override fun onEvent(
                            channelName: String?,
                            eventName: String?,
                            data: String?
                        ) {
                            Log.d(TAG, "onEvent: ${channelName}")
                            showMessage(data)
                        }

                        override fun onSubscriptionSucceeded(channelName: String?) {
                            Log.d(TAG, "onSubscriptionSucceeded: ${channelName}")
                        }

                        override fun onAuthenticationFailure(
                            message: String?,
                            e: java.lang.Exception?
                        ) {
                            Log.d(TAG, "onAuthenticationFailure: ${e?.localizedMessage}")
                        }
                    })

                    Log.d(TAG, "channel $channel bound.")
                }
            }
        } ?: setupPusher()
    }

    private fun goOffline() {
        pusher?.let {
            // 这一步不需要，直接取消channel的订阅即可
//            channel?.unbind(eventName) { channelName, eventName, data ->
//                Log.d(TAG, "unbind succeeded: $channelName - $eventName, data:$data")
//            }
            it.unsubscribe(channelName)
            channel = null
        } ?: setupPusher()
    }

    private fun showMessage(data: String?) {
        val jsonObject = JSONObject(data)

        val message = Message(
            jsonObject["channelName"].toString(),
            jsonObject["user"].toString(),
            jsonObject["message"].toString(),
            jsonObject["time"].toString().toLong()
        )

        runOnUiThread {
            adapter.addMessage(message)
            // scroll the RecyclerView to the last added element
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