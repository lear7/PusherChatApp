package com.gkd.lib_chat.data.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


//@ApiModelProperty("消息ID")
//private val msgId: String = UUID.randomUUID().toString()
//
//@ApiModelProperty("消息状态：0：未读，1：已读，-1：初始状态，-2发送失败")
//private val msgState = 0
//
//@ApiModelProperty("消息目标：UID")
//private val to: String? = null
//
//@ApiModelProperty("消息来源：UID")
//private val from: String? = null
//
//@ApiModelProperty("消息类型int类型(0:text、1:image、2:voice、3:vedio、4:music、5:news、6:msg-tag)")
//private val msgType = 0
//
//@ApiModelProperty("聊天类型int类型(0:未知,1:公聊,2:私聊)")
//private val chatType = 2
//
//@ApiModelProperty("频道名称")
//private val channelName: String? = null
//
//@ApiModelProperty("事件名称")
//private val eventName: String? = null
//
//@ApiModelProperty("集群节点")
//private val cluster: String? = null
//
//@ApiModelProperty("内容")
//private val content: String? = null
//
//@ApiModelProperty("消息时间戳")
//private val createTime: Long? = null

@Entity
data class Message(
    val msgId: String,
    var msgState: Int = -1,
    val to: String? = "",
    val from: String? = "",
    val msgType: Int? = 0,
    val chatType: Int? = 2,
    val channelName: String?,
    val eventName: String?,
    val cluster: String?,
    val content: String? = "",
    var createTime: Long? = 0L,
    var sendState: Int? = 0,
    @Id
    var id: Long? = null
)

