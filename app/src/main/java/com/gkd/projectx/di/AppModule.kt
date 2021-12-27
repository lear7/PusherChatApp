package com.gkd.projectx.di

import android.app.Application
import com.gkd.projectx.App
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