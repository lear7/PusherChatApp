package com.lear.chatdemo.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Message(
    var channelName: String,
    var user: String,
    var message: String,
    @Id
    var time: Long
)