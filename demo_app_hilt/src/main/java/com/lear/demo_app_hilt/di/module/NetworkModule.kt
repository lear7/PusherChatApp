package com.lear.demo_app_hilt.di.module

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.lear.demo_app_hilt.App
import com.lear.demo_app_hilt.di.Driver
import com.lear.demo_app_hilt.di.Truck
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

    private var CHAT_URL = "www.baidu.com"

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
            .baseUrl(CHAT_URL)
            .addConverterFactory((GsonConverterFactory.create()))
            .client(okHttpClient)
            .build()
    }
}