package com.gkd.projectx.di.module

import android.util.Log
import com.gkd.data.services.CharacterService
import com.gkd.data.services.ChatService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private var BASE_URL =
        if (com.gkd.projectx.App.Companion.isRemote) com.gkd.projectx.App.Companion.baseUrl + "pusher/" else com.gkd.projectx.App.Companion.baseUrl

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addConverterFactory(MoshiConverterFactory.create())
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideChatService(retrofit: Retrofit): ChatService {
        Log.e("Chat","provideChatService implementing...")
        return retrofit.create(ChatService::class.java)
    }

    @Singleton
    @Provides
    fun provideCharacterService(retrofit: Retrofit): CharacterService {
        Log.e("Chat","provideCharacterService implementing...")
        return retrofit.create(CharacterService::class.java)
    }
}