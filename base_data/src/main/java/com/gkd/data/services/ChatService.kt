package com.gkd.data.services

import com.gkd.data.model.Sent
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatService {
    @POST("message")
    suspend fun postMessage(@Body message: RequestBody): Call<Sent>

    @POST("delivered")
    suspend fun delivered(@Body messageList: RequestBody): Call<Void>
}