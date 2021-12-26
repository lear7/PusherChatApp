package com.gkd.projectx.di.module

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.gkd.data.services.CharacterService
import com.gkd.data.services.ChatService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private var BASE_URL = "https://rickandmortyapi.com/api/"
    //       if (com.gkd.projectx.App.Companion.isRemote) com.gkd.projectx.App.Companion.baseUrl + "pusher/" else com.gkd.projectx.App.Companion.baseUrl

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
            .baseUrl(BASE_URL)
//            .addConverterFactory(MoshiConverterFactory.create())
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesChatService(retrofit: Retrofit): ChatService {
        return retrofit.create(ChatService::class.java)
    }

    @Singleton
    @Provides
    fun providesCharacterService(retrofit: Retrofit): CharacterService {
        return retrofit.create(CharacterService::class.java)
    }
}