package com.gkd.projectx.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gkd.projectx.App
import com.gkd.projectx.R
import com.gkd.data.model.Message
import com.gkd.projectx.utils.DateUtils


private const val VIEW_TYPE_MY_MESSAGE = 1
private const val VIEW_TYPE_OTHER_MESSAGE = 2

class MessageAdapter(val context: Context) : RecyclerView.Adapter<MessageViewHolder>() {
    private val messages: ArrayList<Message> = ArrayList()

    fun clearMessage() {
        messages.clear()
        notifyDataSetChanged()
    }

    fun setMessage(messageList: ArrayList<Message>) {
        this.messages.addAll(messageList)
        notifyDataSetChanged()
    }

    fun addMessage(message: Message) {
        messages.add(message)
        notifyDataSetChanged()
    }

    fun getMessage(msgId: String): Message? {
        return messages.first {
            it.msgId == msgId
        }
    }

    fun updateMessage(newMessage: Message): Message? {
        messages.forEachIndexed { index, message ->
            if (messages[index].msgId == newMessage.msgId) {
                messages[index] = newMessage
                notifyDataSetChanged()
                return newMessage
            }
        }
        return null
    }

    fun updateMessageState(msgId: String, newState: Int): Message? {
        getMessage(msgId)?.let {
            it.msgState = newState
            return updateMessage(it)
        }
        return null
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages.get(position)
        return if (App.fromUser == message.from) {
            VIEW_TYPE_MY_MESSAGE
        } else {
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if (viewType == VIEW_TYPE_MY_MESSAGE) {
            MyMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_sent_message, parent, false)
            )
        } else {
            OtherMessageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_received_message, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages.get(position)

        holder?.bind(message)
    }

    inner class MyMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.findViewById(R.id.txtMyMessage)
        private var timeText: TextView = view.findViewById(R.id.txtMyMessageTime)
        private var recentText: TextView = view.findViewById(R.id.txtMyMessageRecent)
        private var statusText: TextView = view.findViewById(R.id.txtMyMessageStatus)
        private var sendingIcon: ProgressBar = view.findViewById(R.id.txtMyMessageSending)

        override fun bind(message: Message) {
            messageText.text = message.content
            timeText.text = DateUtils.fromMillisToTimeString(message.createTime ?: 0)
            when (message.msgState) {
                0 -> {
                    statusText.setText("Sent")
                    statusText.visibility = View.VISIBLE
                    recentText.visibility = View.GONE
                    timeText.visibility = View.VISIBLE
                    sendingIcon.visibility = View.GONE
                }
                1 -> {
                    statusText.setText("Delivered")
                    statusText.visibility = View.VISIBLE
                    recentText.visibility = View.GONE
                    timeText.visibility = View.VISIBLE
                    sendingIcon.visibility = View.GONE
                }
                -2 -> {
                    statusText.visibility = View.GONE
                    recentText.visibility = View.VISIBLE
                    timeText.visibility = View.GONE
                    sendingIcon.visibility = View.GONE
                }
                else -> {
                    // -1 init
                    statusText.visibility = View.GONE
                    recentText.visibility = View.GONE
                    timeText.visibility = View.GONE
                    sendingIcon.visibility = View.VISIBLE
                }
            }
        }
    }

    inner class OtherMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.findViewById(R.id.txtOtherMessage)
        private var userText: TextView = view.findViewById(R.id.txtOtherUser)
        private var timeText: TextView = view.findViewById(R.id.txtOtherMessageTime)

        override fun bind(message: Message) {
            messageText.text = message.content
            userText.text = "${message.from}"
            timeText.text = DateUtils.fromMillisToTimeString(message.createTime ?: 0)
        }
    }
}

open class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message: Message) {}
}
