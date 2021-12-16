package com.lear.chatdemo

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatService {
    @POST("message")
    fun postMessage(@Body body: RequestBody): Call<Void>

    companion object {
        private var BASE_URL =
            if (App.isRemote) "http://192.168.10.40:8082/pusher/" else "http://192.168.6.217:8080/"

        fun create(): ChatService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            return retrofit.create(ChatService::class.java)
        }
    }
}