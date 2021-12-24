package com.gkd.projectx.network

import com.gkd.projectx.activity.ui.chat.model.Sent
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatService {
    @POST("message")
    fun postMessage(@Body message: RequestBody): Call<Sent>

    @POST("delivered")
    fun delivered(@Body messageList: RequestBody): Call<Void>
}