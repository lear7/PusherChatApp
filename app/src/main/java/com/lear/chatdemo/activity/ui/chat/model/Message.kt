package com.lear.chatdemo.activity.ui.chat.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


//@ApiModelProperty("消息目标：UID")
//private val to: String? = null
//
//@ApiModelProperty("消息来源：UID")
//private val from: String? = null
//
//@ApiModelProperty("消息类型int类型(0:text、1:image、2:voice、3:vedio、4:music、5:news、6:sys-msg、7:sys-note)")
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
//@ApiModelProperty("内容")
//private val content: String? = null
//
//@ApiModelProperty("扩展字段,JSON对象格式如：{'扩展字段名称':'扩展字段value'}")
//private val extras: String? = null
//
//@ApiModelProperty("消息时间戳")
//private val createTime: Date? = null

@Entity
data class Message(
    val to: String,
    val from: String,
    val msgType: Int = 0,
    val chatType: Int = 2,
    val channelName: String,
    val eventName: String,
    val content: String,
    val extras: String = "",
    var createTime: Long = 0L,
    @Id
    var id: Long?=null
)