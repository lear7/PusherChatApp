package com.lear.demo_app_hilt.di.module

import android.app.Application
import android.content.Context
import com.lear.demo_app_hilt.App
import com.lear.demo_app_hilt.di.Driver
import com.lear.demo_app_hilt.di.Truck
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideApp(application: Application): App {
        return application as App
    }
}