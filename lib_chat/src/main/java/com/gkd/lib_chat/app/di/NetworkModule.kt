package com.gkd.lib_chat.app.di

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.gkd.lib_chat.data.network.ChatService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private var baseUrl = "https://rickandmortyapi.com/api/"

    @Singleton
    @Provides
    fun providesOkhttpCache(@ApplicationContext context: Context): Cache {
        return Cache(context.cacheDir, 1024)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .cache(cache)
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory((GsonConverterFactory.create()))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesChatService(retrofit: Retrofit): ChatService {
        return retrofit.create(ChatService::class.java)
    }
}