package com.lear.chatdemo.di.module

import android.app.Application
import com.lear.chatdemo.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideApp(application: Application): App {
        return application as App
    }
}