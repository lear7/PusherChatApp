package com.lear.demo_app_hilt.di.module

import com.lear.demo_app_hilt.di.ElectricEngine
import com.lear.demo_app_hilt.di.Engine
import com.lear.demo_app_hilt.di.GasEngine
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Qualifier

@Module
@InstallIn(ActivityComponent::class, ViewModelComponent::class)
abstract class EngineModule {

    @BindGasEngine
    @Binds
    abstract fun bindEngine(gasEngine: GasEngine): Engine

    @BindElectricEngine
    @Binds
    abstract fun bindElectricEngine(electricEngine: ElectricEngine): Engine
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindGasEngine

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindElectricEngine